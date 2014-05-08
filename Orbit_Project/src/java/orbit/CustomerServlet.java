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
public class CustomerServlet extends HttpServlet {

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
                
                // make a reservation
                if (request.getParameter("optionsRadios").equals("makeResr"))
                {
                    String passCount = request.getParameter("passCount");
                    // javascript variable that keeps track of how many legs
                    // are in this flight in order to know how many times
                    // to insert reservation into legs table
                    // passed to servlet via hidden input element
                    // make sure it starts from 1 instead of 0 for clarity, since
                    // there is actually a leg number 1 and not a leg number 0
                    String legCount = request.getParameter("legCount");
                    String airlineID = request.getParameter("airlineID");
                    String flightNo = request.getParameter("flightNo");
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
                    
                    for (int i = 1; i <= Integer.parseInt(legCount); i++)
                    {
                        String legNo = request.getParameter("legNo" + i);
                        String fromStopNo = request.getParameter("fromStopNo" + i);
                        String depDate = request.getParameter("depDate" + i);
                        
                        query = "INSERT INTO Legs VALUES (?, ?, ?, ?, ?, ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, resrNo);
                        ps.setInt(2, Integer.parseInt(legNo));
                        ps.setString(3, airlineID);
                        ps.setInt(4, Integer.parseInt(flightNo));
                        ps.setInt(5, Integer.parseInt(fromStopNo));
                        ps.setString(6, depDate);
                    }
                    
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
                // cancel existing reservation
                else if (request.getParameter("optionsRadios").equals("cancelResr"))
                {
                    String resrNo = request.getParameter("resrNo");
                    
                    String query = "DELETE FROM Legs WHERE ResrNo = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(resrNo));
                    ps.executeUpdate();
                    
                    query = "DELETE FROM ReservationPassenger WHERE ResrNo = ?";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(resrNo));
                    ps.executeUpdate();
                    
                    query = "DELETE FROM Reservation WHERE ResrNo = ?";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(resrNo));
                    ps.executeUpdate();
                    
                    conn.commit();
                }
                // view customer's current reservations
                if (request.getParameter("optionsRadios").equals("viewResr"))
                {
                    String accountNo = "2002";
                    //String accountNo = request.getParameter("accountNo");
                    
                    String query = "SELECT * FROM Reservation R\n" +
                                   "WHERE EXISTS (\n" +
                                   "	SELECT * FROM Legs L, StopsAt S\n" +
                                   "	WHERE (R.ResrNo = L.ResrNo AND L.AirlineID = S.AirlineID\n" +
                                   "	AND L.FlightNo = S.FlightNo AND L.FromStopNo = S.StopNo\n" +
                                   "	AND S.DepTime >= '2011-01-05 19:00:00')\n" +
                                   "	AND R.AccountNo = ?)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(accountNo));
                    ResultSet res = ps.executeQuery();
                    
                    json += "{\"data\":[";

                    while (res.next())
                    {
                        json += "{\"resrNo\": \"" + res.getInt("ResrNo") + "\","
                                + "\"resrDate\": \"" + res.getString("ResrDate") + "\","
                                + "\"bookingFee\": \"" + res.getDouble("BookingFee") + "\","
                                + "\"totalFare\": \"" + res.getDouble("TotalFare") + "\","
                                + "\"repSSN\": \"" + res.getInt("RepSSN") + "\","
                                + "\"accountNo\": \"" + res.getInt("AccountNo") + "\"},";
                    }
                    
                    json = json.substring(0, json.length()-1);
                    json += "]}";

                    System.out.println(json);

                    out.print(json);
                }
                // travel itinerary for given reservation
                if (request.getParameter("optionsRadios").equals("travelItin"))
                {
                    String resrNo = "111";
                    // String resrNo = request.getParameter("resrNo");
                    
                    String query = "SELECT DISTINCT L.ResrNo, L.AirlineID, L.FlightNo, "
                                    + "DA.Name AS Departing, AA.Name AS Arriving, F.DepTime, "
                                    + "F.ArrTime\n" +
                                    "FROM Legs L, FlightSchedule F, Airport DA, Airport AA\n" +
                                    "WHERE (L.AirlineID = F.AirlineID AND L.FlightNo = F.FlightNo "
                                    + "AND F.FromAirport = DA.Id AND F.ToAirport = AA.Id "
                                    + "AND L.ResrNo = ?)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(resrNo));
                    ResultSet res = ps.executeQuery();
                    
                    json += "{\"data\":[";

                    while (res.next())
                    {
                        json += "{\"resrNo\": \"" + res.getInt("ResrNo") + "\","
                                + "\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                + "\"departing\": \"" + res.getString("Departing") + "\","
                                + "\"arriving\": \"" + res.getString("Arriving") + "\","
                                + "\"depTime\": \"" + res.getString("DepTime") + "\","
                                + "\"arrTime\": \"" + res.getString("ArrTime") + "\"},";
                    }
                    
                    json = json.substring(0, json.length()-1);
                    json += "]}";

                    System.out.println(json);

                    out.print(json);
                }
                // customer's current bid on given reverse auction
                if (request.getParameter("optionsRadios").equals("viewCurrBid"))
                {
                    String accountNo = "2001";
                    String airlineID = "JA";
                    String flightNo = "111";
                    String seatClass = "First";
                    /*String accountNo = request.getParameter("accountNo");
                    String airlineID = request.getParameter("airlineID");
                    String flightNo = request.getParameter("flightNo");
                    String seatClass = request.getParameter("seatClass");*/
                    
                    String query = "SELECT * FROM Auctions\n" +
                                   "WHERE (AccountNo = ? AND AirlineID = ? AND FlightNo = ?\n" +
                                   "AND Class = ?)\n" +
                                   "ORDER BY BidDate DESC LIMIT 0,1";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(accountNo));
                    ps.setString(2, airlineID);
                    ps.setInt(3, Integer.parseInt(flightNo));
                    ps.setString(4, seatClass);
                    ResultSet res = ps.executeQuery();
                    
                    json += "{\"data\":[";

                    while (res.next())
                    {
                        json += "{\"accountNo\": \"" + res.getInt("AccountNo") + "\","
                                + "\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                + "\"class\": \"" + res.getString("Class") + "\","
                                + "\"bidDate\": \"" + res.getString("BidDate") + "\","
                                + "\"nYOP\": \"" + res.getDouble("NYOP") + "\","
                                // this is tricky --- could be boolean or int (need to test what happens)
                                + "\"accepted\": \"" + res.getBoolean("Accepted") + "\"},";
                    }
                    
                    json = json.substring(0, json.length()-1);
                    json += "]}";

                    System.out.println(json);

                    out.print(json);
                }
                // bid history for given reverse auction
                if (request.getParameter("optionsRadios").equals("viewBidHist"))
                {
                    String airlineID = "JA";
                    String flightNo = "111";
                    String seatClass = "First";
                    /*String airlineID = request.getParameter("airlineID");
                    String flightNo = request.getParameter("flightNo");
                    String seatClass = request.getParameter("seatClass");*/
                    
                    String query = "SELECT * FROM Auctions\n" +
                                   "WHERE (AirlineID = ? AND FlightNo = ? AND Class = ?)\n" +
                                    "ORDER BY BidDate DESC";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, airlineID);
                    ps.setInt(2, Integer.parseInt(flightNo));
                    ps.setString(3, seatClass);
                    ResultSet res = ps.executeQuery();
                    
                    json += "{\"data\":[";

                    while (res.next())
                    {
                        json += "{\"accountNo\": \"" + res.getInt("AccountNo") + "\","
                                + "\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                + "\"class\": \"" + res.getString("Class") + "\","
                                + "\"bidDate\": \"" + res.getString("BidDate") + "\","
                                + "\"nYOP\": \"" + res.getDouble("NYOP") + "\","
                                // this is tricky --- could be boolean or int (need to test what happens)
                                + "\"accepted\": \"" + res.getBoolean("Accepted") + "\"},";
                    }
                    
                    json = json.substring(0, json.length()-1);
                    json += "]}";

                    System.out.println(json);

                    out.print(json);
                }
                // history of customer's current and past reservations
                if (request.getParameter("optionsRadios").equals("viewResrHist"))
                {
                    String accountNo = "2001";
                    //String accountNo = request.getParameter("accountNo");
                    
                    String query = "SELECT * FROM Reservation WHERE AccountNo = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(accountNo));
                    ResultSet res = ps.executeQuery();
                    
                    json += "{\"data\":[";

                    while (res.next())
                    {
                        json += "{\"resrNo\": \"" + res.getInt("ResrNo") + "\","
                                + "\"resrDate\": \"" + res.getString("ResrDate") + "\","
                                + "\"bookingFee\": \"" + res.getDouble("BookingFee") + "\","
                                + "\"totalFare\": \"" + res.getDouble("TotalFare") + "\","
                                + "\"repSSN\": \"" + res.getInt("RepSSN") + "\","
                                + "\"accountNo\": \"" + res.getInt("AccountNo") + "\"},";
                    }
                    
                    json = json.substring(0, json.length()-1);
                    json += "]}";

                    System.out.println(json);

                    out.print(json);
                }
                // best-seller list of flights
                if (request.getParameter("optionsRadios").equals("viewBestSell"))
                {
                    String query = "SELECT L.AirlineID, L.FlightNo, COUNT(DISTINCT L.ResrNo) AS "
                                   + "ResrCount\n" +
                                   "FROM Legs L\n" +
                                   "GROUP BY L.AirlineID, L.FlightNo\n" +
                                   "ORDER BY ResrCount DESC, L.AirlineID ASC, L.FlightNo ASC";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ResultSet res = ps.executeQuery();
                    
                    json += "{\"data\":[";

                    while (res.next())
                    {
                        json += "{\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                + "\"resrCount\": \"" + res.getInt("ResrCount") + "\"},";
                    }
                    
                    json = json.substring(0, json.length()-1);
                    json += "]}";

                    System.out.println(json);

                    out.print(json);
                }
                // personalized flight suggestion list
                else if (request.getParameter("optionsRadios").equals("flightSuggest"))
                {
                    String accountNo = "2001";
                    //String accountNo = request.getParameter("accountNo");
                    
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
                // place bid in reverse auction
                else if (request.getParameter("optionsRadios").equals("placeBid"))
                {
                    String accountNo = "2003";
                    String airlineID = "DA";
                    String flightNo = "123";
                    String seatClass = "Economy";
                    String nYOP = "750.0";
                    /*String accountNo = request.getParameter("accountNo");
                    String airlineID = request.getParameter("airlineID");
                    String flightNo = request.getParameter("flightNo");
                    String seatClass = request.getParameter("seatClass");
                    String nYOP = request.getParameter("nYOP");*/
                    
                    String query = "INSERT INTO Auctions VALUES (?, ?, ?, ?, NOW(), ?, NULL)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(accountNo));
                    ps.setString(2, airlineID);
                    ps.setInt(3, Integer.parseInt(flightNo));
                    ps.setString(4, seatClass);
                    ps.setDouble(5, Double.parseDouble(nYOP));
                    ps.executeUpdate();
                    
                    conn.commit();
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
