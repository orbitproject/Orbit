/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orbit;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
public class FlightListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String leaving_from = request.getParameter("leaving-from");
        String going_to = request.getParameter("going-to");
        String departing_on = request.getParameter("departing-on");
        
        //List<String> jsonData = new ArrayList();
        String json = "";
        String mysJDBCDriver = "com.mysql.jdbc.Driver";
        String mysURL = "jdbc:mysql://localhost:3306/cse_305_project_transactions?zeroDateTimeBehavior=convertToNull";
        String mysUserID = "root";
        String mysPassword = "root";
        Connection conn=null;
        
        try (PrintWriter out = response.getWriter()) {
        try {
                Class.forName(mysJDBCDriver).newInstance();
                Properties sysprops=System.getProperties();
                sysprops.put("user",mysUserID);
                sysprops.put("password",mysPassword);

                //connect to the database
                conn = DriverManager.getConnection(mysURL,sysprops);
                System.out.println("Connected successfully to database using JConnect");

                conn.setAutoCommit(false);
                
                String query = "DROP VIEW FlightSchedule";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.executeUpdate();
                
                query = "CREATE VIEW FlightSchedule(AirlineID, FlightNo, LegNo, DepTime, FromAirport, "
                        + "ArrTime, ToAirport) AS\n" +
                        "SELECT SD.AirlineID, SD.FlightNo, SD.StopNo, SD.DepTime, SD.AirportID, "
                        + "SA.ArrTime, SA.AirportID\n" +
                        "FROM StopsAt SD, StopsAt SA\n" +
                        "WHERE (SD.AirlineID = SA.AirlineID AND SD.FlightNo = SA.FlightNo "
                        + "AND SD.StopNo = SA.StopNo - 1)";
                ps = conn.prepareStatement(query);
                ps.executeUpdate();
                
                conn.commit();
                
                query = "SELECT DISTINCT F1.AirlineID, F1.FlightNo\n" +
                        "FROM FlightSchedule F1, FlightSchedule F2\n" +
                        "WHERE (F1.FromAirport = ? AND F2.ToAirport = ? AND F1.DepTime LIKE ?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, leaving_from);
                ps.setString(2, going_to);
                ps.setString(3, departing_on + "%");
                ResultSet res = ps.executeQuery();
                String airlineID = "";
                int flightNo = 0;
                
                while (res.next())
                {
                    airlineID = res.getString("AirlineID");
                    flightNo = res.getInt("FlightNo");
                }
                
                query = "SELECT * FROM FlightSchedule\n" +
                        "WHERE (AirlineID = ? AND FlightNo = ?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, airlineID);
                ps.setInt(2, flightNo);
                res = ps.executeQuery();
                
                json += "{\"data\":[";

                    while (res.next())
                    {
                        json += "{\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                + "\"legNo\": \"" + res.getInt("LegNo") + "\","
                                + "\"depTime\": \"" + res.getString("DepTime") + "\","
                                + "\"fromAirport\": \"" + res.getString("FromAirport") + "\","
                                + "\"arrTime\": \"" + res.getString("ArrTime") + "\","
                                + "\"toAirport\": \"" + res.getString("ToAirport") + "\"},";
                    }

                    json = json.substring(0, json.length()-1);

                    json += "]}";

                    System.out.println(json);

                    out.print(json);
                
        } catch(Exception e){
                e.printStackTrace();
        }
        finally{
                try{conn.close();}catch(Exception ee){};
        }
        
        /*response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            for (int i = 0; i < jsonData.size(); i++)
            {
                out.print(jsonData.get(i));
            }
            out.flush();
        }*/
    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
