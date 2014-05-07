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
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jessica
 */
public class CustomerRepServlet extends HttpServlet {

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
                
                // record reservation
                if (request.getParameter("optionsRadios").equals(""))
                {
                    String passCount = request.getParameter("passCount");
                    String accountNo = request.getParameter("accountNo");
                    String bookingFee = request.getParameter("bookingFee");
                    String totalFare = request.getParameter("totalFare");
                    String repSSN = request.getParameter("repSSN");
                    String cSeatNo = request.getParameter("cSeatNo");
                    String cSeatClass = request.getParameter("cSeatClass");
                    String cMeal = request.getParameter("cMeal");
                    
                    String query = "SELECT * FROM Reservation";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ResultSet res = ps.executeQuery();
                    int resrNo = 1;

                    if (res.next())
                    {
                        res.last();
                        resrNo = res.getInt("ResrNo") + 1;
                    }
                    
                    query = "INSERT INTO Reservation VALUES (?, NOW(), ?, ?, ?, ?";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, resrNo);
                    ps.setDouble(2, Double.parseDouble(bookingFee));
                    ps.setDouble(3, Double.parseDouble(totalFare));
                    ps.setInt(4, Integer.parseInt(repSSN));
                    ps.setInt(5, Integer.parseInt(accountNo));
                    ps.executeUpdate();
                    
                    // retrieve the id of the customer under whose account this reservation
                    // is being made
                    query = "SELECT * FROM Customer where AccountNo = ?";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(accountNo));
                    res = ps.executeQuery();
                    int id = 1;

                    if (res.next())
                    {
                        id = res.getInt("Id");
                    }
                    
                    query = "INSERT INTO ReservationPassenger VALUES (?, ?, ?, ?, ?, ?)";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, resrNo);
                    ps.setInt(2, id);
                    ps.setInt(3, Integer.parseInt(accountNo));
                    ps.setString(4, cSeatNo);
                    ps.setString(5, cSeatClass);
                    ps.setString(6, cMeal);
                    
                    conn.commit();
                    
                    for (int i = 0; i < Integer.parseInt(passCount); i++)
                    {
                        String firstName = request.getParameter("firstName" + i);
                        String lastName = request.getParameter("lastName" + i);
                        String address = request.getParameter("address" + i);
                        String city = request.getParameter("city" + i);
                        String state = request.getParameter("state" + i);
                        String zip = request.getParameter("zip" + i);
                        String seatNo = request.getParameter("seatNo" + i);
                        String seatClass = request.getParameter("seatClass" + i);
                        String meal = request.getParameter("meal" + i);
                        
                        query = "SELECT * FROM Person";
                        ps = conn.prepareStatement(query);
                        res = ps.executeQuery();
                        id = 1;

                        if (res.next())
                        {
                            res.last();
                            id = res.getInt("Id") + 1;
                        }
                        
                        query = "INSERT INTO Person VALUES (?, ?, ?, ?, ?, ?, ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, id);
                        ps.setString(2, firstName);
                        ps.setString(3, lastName);
                        ps.setString(4, address);
                        ps.setString(5, city);
                        ps.setString(6, state);
                        ps.setInt(7, Integer.parseInt(zip));
                        ps.executeUpdate();
                        
                        query = "INSERT INTO Passenger VALUES (?, ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, id);
                        ps.setInt(2, Integer.parseInt(accountNo));
                        ps.executeUpdate();
                        
                        query = "INSERT INTO ReservationPassenger VALUES (?, ?, ?, ?, ?, ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, resrNo);
                        ps.setInt(2, id);
                        ps.setInt(3, Integer.parseInt(accountNo));
                        ps.setString(4, seatNo);
                        ps.setString(5, seatClass);
                        ps.setString(6, meal);
                        
                        conn.commit();
                    }
                }
                // add customer
                else if (request.getParameter("optionsRadios").equals(""))
                {
                        String firstName = request.getParameter("firstName");
                        String lastName = request.getParameter("lastName");
                        String address = request.getParameter("address");
                        String city = request.getParameter("city");
                        String state = request.getParameter("state");
                        String zip = request.getParameter("zip");
                        String creditCardNo = request.getParameter("creditCardNo");
                        String email = request.getParameter("email");
                        String rating = request.getParameter("rating");
                        String password = request.getParameter("password");
                        
                        String query = "SELECT * FROM Person";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ResultSet res = ps.executeQuery();
                        int id = 1;

                        if (res.next())
                        {
                            res.last();
                            id = res.getInt("Id") + 1;
                        }
                        
                        query = "INSERT INTO Person VALUES (?, ?, ?, ?, ?, ?, ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, id);
                        ps.setString(2, firstName);
                        ps.setString(3, lastName);
                        ps.setString(4, address);
                        ps.setString(5, city);
                        ps.setString(6, state);
                        ps.setInt(7, Integer.parseInt(zip));
                        ps.executeUpdate();
                        
                        query = "SELECT * FROM Customer";
                        ps = conn.prepareStatement(query);
                        res = ps.executeQuery();
                        int accountNo = 1;

                        if (res.next())
                        {
                            res.last();
                            accountNo = res.getInt("AccountNo") + 1;
                        }
                        
                        query = "INSERT INTO Customer VALUES (?, ?, ?, ?, NOW(), ?, ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, id);
                        ps.setInt(2, accountNo);
                        ps.setString(3, creditCardNo);
                        ps.setString(4, email);
                        if (rating.trim().equals(""))
                            ps.setString(5, null);
                        else
                            ps.setInt(5, Integer.parseInt(rating));
                        ps.setString(6, password);
                        ps.executeUpdate();
                        
                        query = "INSERT INTO Passenger VALUES (?, ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, id);
                        ps.setInt(2, accountNo);
                        ps.executeUpdate();
                        
                        conn.commit();
                }
                // update customer
                else if (request.getParameter("optionsRadios").equals(""))
                {
                        String accountNo = request.getParameter("accountNo");
                        String creditCardNo = request.getParameter("creditCardNo");
                        String email = request.getParameter("email");
                        String rating = request.getParameter("rating");
                        String password = request.getParameter("password");

                        String query;
                        PreparedStatement ps;

                        if (!creditCardNo.trim().equals(""))
                        {
                            query = "UPDATE Customer SET CreditCardNo = ? WHERE AccountNo = ?";
                            ps = conn.prepareStatement(query);
                            ps.setString(1, creditCardNo);
                            ps.setInt(2, Integer.parseInt(accountNo));
                            ps.executeUpdate();
                            conn.commit();
                        }

                        if (!email.trim().equals(""))
                        {
                            query = "UPDATE Customer SET Email = ? WHERE AccountNo = ?";
                            ps = conn.prepareStatement(query);
                            ps.setString(1, email);
                            ps.setInt(2, Integer.parseInt(accountNo));
                            ps.executeUpdate();
                            conn.commit();
                        }
                        
                        if (!rating.trim().equals(""))
                        {
                            query = "UPDATE Customer SET Rating = ? WHERE AccountNo = ?";
                            ps = conn.prepareStatement(query);
                            ps.setInt(1, Integer.parseInt(rating));
                            ps.setInt(2, Integer.parseInt(accountNo));
                            ps.executeUpdate();
                            conn.commit();
                        }
                        
                        if (!password.trim().equals(""))
                        {
                            query = "UPDATE Customer SET Password = ? WHERE AccountNo = ?";
                            ps = conn.prepareStatement(query);
                            ps.setString(1, password);
                            ps.setInt(2, Integer.parseInt(accountNo));
                            ps.executeUpdate();
                            conn.commit();
                        }
                }
                // delete customer
                else if (request.getParameter("optionsRadios").equals(""))
                {
                        String accountNo = request.getParameter("accountNo");

                        String query = "DELETE FROM Customer WHERE AccountNo = ?";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setInt(1, Integer.parseInt(accountNo));
                        ps.executeUpdate();
                        conn.commit();
                }
                // produce customer mailing lists
                else if (request.getParameter("optionsRadios").equals(""))
                {
                    String query = "SELECT P.FirstName, P.LastName, C.Email, P.Address, P.City, "
                                    + "P.State, P.ZipCode\n" +
                                    "FROM Person P, Customer C\n" +
                                    "WHERE P.Id = C.Id";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ResultSet res = ps.executeQuery();
                    
                    json += "{\"data\":[";

                    while (res.next())
                    {
                        json += "{\"firstName\": \"" + res.getString("FirstName") + "\","
                                + "\"lastName\": \"" + res.getString("LastName") + "\","
                                + "\"email\": \"" + res.getString("Email") + "\","
                                + "\"address\": \"" + res.getString("Address") + "\","
                                + "\"city\": \"" + res.getString("City") + "\","
                                + "\"state\": \"" + res.getString("State") + "\","
                                + "\"zipCode\": \"" + res.getDouble("ZipCode") + "\"},";
                    }
                    
                    json = json.substring(0, json.length()-1);
                    json += "]}";

                    System.out.println(json);

                    out.print(json);
                }
                // produce list of flight suggestions
                else if (request.getParameter("optionsRadios").equals(""))
                {
                    String accountNo = request.getParameter("accountNo");
                    
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
                    
                    query = "DROP VIEW CustFlightHistory";
                    ps = conn.prepareStatement(query);
                    ps.executeUpdate();
                    
                    query = "CREATE VIEW CustFlightHistory(AccountNo, AirlineID, FlightNo, LegNo, "
                            + "DepTime, FromAirport, ArrTime, ToAirport) AS\n" +
                            "SELECT DISTINCT R.AccountNo, F.AirlineID, F.FlightNo, F.LegNo, F.DepTime, "
                            + "F.FromAirport, F.ArrTime, F.ToAirport\n" +
                            "FROM Reservation R, Legs L, FlightSchedule F\n" +
                            "WHERE (R.ResrNo = L.ResrNo AND L.AirlineID = F.AirlineID AND "
                            + "L.FlightNo = F.FlightNo)";
                    ps = conn.prepareStatement(query);
                    ps.executeUpdate();
                    
                    query = "DROP VIEW CustDestLeg";
                    ps = conn.prepareStatement(query);
                    ps.executeUpdate();
                    
                    query = "CREATE VIEW CustDestLeg(AccountNo, AirlineID, FlightNo, DestLegNo) AS\n" +
                            "SELECT DISTINCT C.AccountNo, C.AirlineID, C.FlightNo, MAX(C.LegNo)\n" +
                            "FROM CustFlightHistory C\n" +
                            "GROUP BY C.AccountNo, C.AirlineID, C.FlightNo";
                    ps = conn.prepareStatement(query);
                    ps.executeUpdate();
                    
                    query = "DROP VIEW CustDestHistory";
                    ps = conn.prepareStatement(query);
                    ps.executeUpdate();
                    
                    query = "CREATE VIEW CustDestHistory(AccountNo, AirlineID, FlightNo, DestAirport) "
                            + "AS\n" +
                            "SELECT DISTINCT C.AccountNo, C.AirlineID, C.FlightNo, C.ToAirport\n" +
                            "FROM CustFlightHistory C, CustDestLeg D\n" +
                            "WHERE (C.AccountNo = D.AccountNo AND C.AirlineID = D.AirlineID \n" +
                            "AND C.FlightNo = D.FlightNo AND C.LegNo = D.DestLegNo)";
                    ps = conn.prepareStatement(query);
                    ps.executeUpdate();
                    
                    conn.commit();
                    
                    query = "SELECT F1.AirlineID, F1.FlightNo, F1.LegNo, F1.DepTime, F1.FromAirport, "
                            + "F1.ArrTime, F1.ToAirport\n" +
                            "FROM FlightSchedule F1, FlightSchedule F2, CustDestHistory C\n" +
                            "WHERE (F1.AirlineID = F2.AirlineID AND F1.FlightNo = F2.FlightNo "
                            + "AND F2.ToAirport = C.DestAirport \n" +
                            "AND NOT (F1.AirlineID = C.AirlineID AND F1.FlightNo = C.FlightNo)  \n" +
                            "AND F1.DepTime > NOW() AND C.AccountNo = ?)";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(accountNo));
                    ResultSet res = ps.executeQuery();
                    
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
                }
        } catch(Exception e){
                e.printStackTrace();
        }
        finally{
                try{conn.close();}catch(Exception ee){};
        }
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
