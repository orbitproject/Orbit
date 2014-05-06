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
public class ManagerServlet extends HttpServlet {

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
                
                // employee
                if (request.getParameter("optionsRadios").equals("employees"))
                {
                    // add
                    if (request.getParameter("empActions").equals("add"))
                    {
                        String firstName = "Ben";
                        String lastName = "Stevens";
                        String address = "234 Orbit Dr";
                        String city = "Stony Brook";
                        String state = "New York";
                        String zip = "11790";
                        String ssn = "987685434";
                        String isManagerString = "no";
                        boolean isManager;
                        if (isManagerString.toUpperCase().equals("YES"))
                            isManager = true;
                        else
                            isManager = false;
                        String startDate = "2011-05-05";
                        String hourlyRate = "12.00";
                        String email = "ben.stevens@gmail.com";
                        String password = "test";
                        /*String firstName = request.getParameter("placeholder");
                        String lastName = request.getParameter("placeholder");
                        String address = request.getParameter("placeholder");
                        String city = request.getParameter("placeholder");
                        String state = request.getParameter("placeholder");
                        String zip = request.getParameter("placeholder");
                        String ssn = request.getParameter("placeholder");
                        String isManagerString = request.getParameter("placeholder");
                        boolean isManager;
                        if (isManagerString.equals("placeholder"))
                            isManager = true;
                        else
                            isManager = false;
                        String startDate = request.getParameter("placeholder");
                        String hourlyRate = request.getParameter("placeholder");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");*/

                        String query = "SELECT * FROM Person";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ResultSet res = ps.executeQuery();
                        int id = 1;

                        if (res.next())
                        {
                            res.last();
                            id = res.getInt("Id") + 1;
                        }

                        query = "INSERT INTO Person VALUES(?, ?, ?, ?, ?, ?, ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, id);
                        ps.setString(2, firstName);
                        ps.setString(3, lastName);
                        ps.setString(4, address);
                        ps.setString(5, city);
                        ps.setString(6, state);
                        ps.setInt(7, Integer.parseInt(zip));
                        ps.executeUpdate();

                        query = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?, ?, ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, id);
                        ps.setInt(2, Integer.parseInt(ssn));
                        ps.setBoolean(3, isManager);
                        ps.setString(4, startDate);
                        ps.setDouble(5, Double.parseDouble(hourlyRate));
                        ps.setString(6, email);
                        ps.setString(7, password);
                        ps.executeUpdate();

                        conn.commit();
                    }
                    // update
                    else if (request.getParameter("empActions").equals("update"))
                    {
                        String ssn = "987685434";
                        String isManagerString = "yes";
                        boolean isManager;
                        if (isManagerString.toUpperCase().equals("YES"))
                            isManager = true;
                        else
                            isManager = false;
                        String hourlyRate = "14.00";
                        String email = "";
                        String password = "";
                        /*String ssn = request.getParameter("placeholder");
                        String isManagerString = request.getParameter("placeholder");
                        boolean isManager;
                        if (isManagerString.equals("placeholder"))
                            isManager = true;
                        else
                            isManager = false;
                        String hourlyRate = request.getParameter("placeholder");
                        String email = request.getParameter("placeholder");
                        String password = request.getParameter("placeholder");*/

                        String query;
                        PreparedStatement ps;

                        if (!isManagerString.trim().equals(""))
                        {
                            query = "UPDATE Employee SET IsManager = ? WHERE SSN = ?";
                            ps = conn.prepareStatement(query);
                            ps.setBoolean(1, isManager);
                            ps.setInt(2, Integer.parseInt(ssn));
                            ps.executeUpdate();
                            conn.commit();
                        }

                        if (!hourlyRate.trim().equals(""))
                        {
                            query = "UPDATE Employee SET HourlyRate = ? WHERE SSN = ?";
                            ps = conn.prepareStatement(query);
                            ps.setDouble(1, Double.parseDouble(hourlyRate));
                            ps.setInt(2, Integer.parseInt(ssn));
                            ps.executeUpdate();
                            conn.commit();
                        }
                        
                        if (!email.trim().equals(""))
                        {
                            query = "UPDATE Employee SET Email = ? WHERE SSN = ?";
                            ps = conn.prepareStatement(query);
                            ps.setString(1, email);
                            ps.setInt(2, Integer.parseInt(ssn));
                            ps.executeUpdate();
                            conn.commit();
                        }
                        
                        if (!password.trim().equals(""))
                        {
                            query = "UPDATE Employee SET Password = ? WHERE SSN = ?";
                            ps = conn.prepareStatement(query);
                            ps.setString(1, password);
                            ps.setInt(2, Integer.parseInt(ssn));
                            ps.executeUpdate();
                            conn.commit();
                        }
                    }
                    // delete
                    else if (request.getParameter("empActions").equals("delete"))
                    {
                        String ssn = "987685434";
                        //String ssn = request.getParameter("placeholder");

                        String query = "DELETE FROM Employee WHERE SSN = ?";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setInt(1, Integer.parseInt(ssn));
                        ps.executeUpdate();
                        conn.commit();
                    }
                }
                // obtain sales report for particular month
                else if (request.getParameter("optionsRadios").equals("salesreport"))
                {
                    String afterDate = "2010-12-31";
                    String beforeDate = "2011-02-01";
                    /*String afterDate = request.getParameter("sales-month-afterdate");
                    String beforeDate = request.getParameter("sales-month-beforedate");*/
                    
                    String query = "SELECT DISTINCT L.AirlineID, L.FlightNo, SUM(R.TotalFare) "
                            + "AS TotalSales\n" +
                            "FROM Legs L, Reservation R\n" +
                            "WHERE (L.ResrNo = R.ResrNo AND R.ResrDate > ? "
                            + "AND R.ResrDate < ?)\n" +
                            "GROUP BY L.AirlineID, L.FlightNo";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, afterDate);
                    ps.setString(2, beforeDate);
                    ResultSet res = ps.executeQuery();
                    
                    json += "{\"data\":[";

                    while (res.next())
                    {
                        json += "{\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                + "\"totalSales\": \"" + res.getDouble("TotalSales") + "\"},";
                    }
                    
                    json = json.substring(0, json.length()-1);
                    json += "]}";

                    System.out.println(json);

                    out.print(json);
                }
                // flights
                else if (request.getParameter("optionsRadios").equals("flights"))
                {
                    // comprehensive listing of all flights
                    if (request.getParameter("flightActions").equals("allFlights"))
                    {
                        String query = "SELECT * FROM Flight";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ResultSet res = ps.executeQuery();

                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                    + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                    + "\"noOfSeats\": \"" + res.getInt("NoOfSeats") + "\","
                                    + "\"daysOperating\": \"" + res.getString("DaysOperating") + "\","
                                    + "\"minLengthOfStay\": \"" + res.getInt("MinLengthOfStay") + "\","
                                    + "\"maxLengthOfStay\": \"" + res.getInt("MaxLengthOfStay") + "\"},";
                        }

                        json = json.substring(0, json.length()-1);
                        json += "]}";

                        System.out.println(json);

                        out.print(json);
                    }
                    // list of all flights for given airport
                    else if (request.getParameter("flightActions").equals("flightsForAirport"))
                    {
                        String airportID = "LGA";
                        //String airportID = request.getParameter("placeholder");

                        String query = "SELECT DISTINCT F.*\n" +
                                       "FROM Flight F, StopsAt S\n" +
                                       "WHERE (F.AirlineID = S.AirlineID AND F.FlightNo = S.FlightNo\n" +
                                       "AND S.AirportID = ?)";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, airportID);
                        ResultSet res = ps.executeQuery();

                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                    + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                    + "\"noOfSeats\": \"" + res.getInt("NoOfSeats") + "\","
                                    + "\"daysOperating\": \"" + res.getString("DaysOperating") + "\","
                                    + "\"minLengthOfStay\": \"" + res.getInt("MinLengthOfStay") + "\","
                                    + "\"maxLengthOfStay\": \"" + res.getInt("MaxLengthOfStay") + "\"},";
                        }

                        json = json.substring(0, json.length()-1);
                        json += "]}";

                        System.out.println(json);

                        out.print(json);
                    }
                    // list of most active flights
                    else if (request.getParameter("flightActions").equals("mostActive"))
                    {
                        String query = "SELECT L.AirlineID, L.FlightNo, COUNT(DISTINCT L.ResrNo) "
                                       + "AS ResrCount\n" +
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
                    // list of flights whose arrival and departure times on-time
                    else if (request.getParameter("flightActions").equals("onTime"))
                    {
                            String query = "SELECT * FROM Flight F\n" +
                                    "WHERE NOT EXISTS (\n" +
                                    "	SELECT * FROM StopsAt S\n" +
                                    "	WHERE F.AirlineID = S.AirlineID AND F.FlightNo = S.FlightNo\n" +
                                    "	AND (S.ActualArrTime > S.ArrTime OR S.ActualDepTime > S.DepTime))";
                            PreparedStatement ps = conn.prepareStatement(query);
                            ResultSet res = ps.executeQuery();

                            json += "{\"data\":[";

                            while (res.next())
                            {
                                json += "{\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                        + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                        + "\"noOfSeats\": \"" + res.getInt("NoOfSeats") + "\","
                                        + "\"daysOperating\": \"" + res.getString("DaysOperating") + "\","
                                        + "\"minLengthOfStay\": \"" + res.getInt("MinLengthOfStay") + "\","
                                        + "\"maxLengthOfStay\": \"" + res.getInt("MaxLengthOfStay") + "\"},";
                            }

                            json = json.substring(0, json.length()-1);
                            json += "]}";

                            System.out.println(json);

                            out.print(json);
                    }
                    // list of flights whose arrival and departure times delayed
                    else if(request.getParameter("flightActions").equals("delayed"))
                    {
                            String query = "SELECT * FROM Flight F\n" +
                                    "WHERE EXISTS (\n" +
                                    "	SELECT * FROM StopsAt S\n" +
                                    "	WHERE F.AirlineID = S.AirlineID AND F.FlightNo = S.FlightNo\n" +
                                    "	AND (S.ActualArrTime > S.ArrTime OR S.ActualDepTime > S.DepTime))";
                            PreparedStatement ps = conn.prepareStatement(query);
                            ResultSet res = ps.executeQuery();

                            json += "{\"data\":[";

                            while (res.next())
                            {
                                json += "{\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                        + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                        + "\"noOfSeats\": \"" + res.getInt("NoOfSeats") + "\","
                                        + "\"daysOperating\": \"" + res.getString("DaysOperating") + "\","
                                        + "\"minLengthOfStay\": \"" + res.getInt("MinLengthOfStay") + "\","
                                        + "\"maxLengthOfStay\": \"" + res.getInt("MaxLengthOfStay") + "\"},";
                            }

                            json = json.substring(0, json.length()-1);
                            json += "]}";

                            System.out.println(json);

                            out.print(json);
                    }
                }
                // produce a list of reservations
                else if (request.getParameter("optionsRadios").equals("reservations"))
                {
                    String query;
                    PreparedStatement ps;
                    ResultSet res;
                    
                    // by flight number
                    if (request.getParameter("res-search-type").equals("0"))
                    {
                        String airlineID = "AA";
                        String flightNo = "111";
                        /*String airlineID = request.getParameter("resAirlineID");
                        String flightNo = request.getParameter("resFlightNo");*/
                        
                        query = "SELECT DISTINCT R.ResrNo, R.ResrDate, R.BookingFee, R.TotalFare, "
                                + "R.RepSSN, P.FirstName, P.LastName\n" +
                                "FROM Reservation R, Customer C, Person P, Legs L\n" +
                                "WHERE (R.AccountNo = C.AccountNo AND C.Id = P.Id "
                                + "AND R.ResrNo = L.ResrNo AND L.AirlineID = ? "
                                + "AND L.FlightNo = ?)";
                        ps = conn.prepareStatement(query);
                        ps.setString(1, airlineID);
                        ps.setString(2, flightNo);
                        res = ps.executeQuery();
                        
                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"resrNo\": \"" + res.getString("ResrNo") + "\","
                                + "\"resrDate\": \"" + res.getString("ResrDate") + "\","
                                + "\"bookingFee\": \"" + res.getDouble("BookingFee") + "\","
                                + "\"totalFare\": \"" + res.getDouble("TotalFare") + "\","
                                + "\"repSSN\": \"" + res.getString("RepSSN") + "\","
                                + "\"firstName\": \"" + res.getString("FirstName") + "\","
                                + "\"lastName\": \"" + res.getString("LastName") + "\"},";
                        }

                        json = json.substring(0, json.length()-1);
                        json += "]}";

                        System.out.println(json);

                        out.print(json);
                    }
                    // by customer name
                    if (request.getParameter("res-search-type").equals("1"))
                    {
                        String firstName = "John";
                        String lastName = "Doe";
                        /*String firstName = request.getParameter("resFirstName");
                        String lastName = request.getParameter("resLastName");*/
                        
                        query = "SELECT DISTINCT R.ResrNo, R.ResrDate, R.BookingFee, R.TotalFare, "
                                + "R.RepSSN, P.FirstName, P.LastName\n" +
                                "FROM Reservation R, Customer C, Person P\n" +
                                "WHERE R.AccountNo = C.AccountNo AND C.Id = P.Id AND\n" +
                                "P.FirstName = ? AND P.LastName = ?";
                        ps = conn.prepareStatement(query);
                        ps.setString(1, firstName);
                        ps.setString(2, lastName);
                        res = ps.executeQuery();
                        
                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"resrNo\": \"" + res.getString("ResrNo") + "\","
                                + "\"resrDate\": \"" + res.getString("ResrDate") + "\","
                                + "\"bookingFee\": \"" + res.getDouble("BookingFee") + "\","
                                + "\"totalFare\": \"" + res.getDouble("TotalFare") + "\","
                                + "\"repSSN\": \"" + res.getString("RepSSN") + "\","
                                + "\"firstName\": \"" + res.getString("FirstName") + "\","
                                + "\"lastName\": \"" + res.getString("LastName") + "\"},";
                        }

                        json = json.substring(0, json.length()-1);
                        json += "]}";

                        System.out.println(json);

                        out.print(json);
                    }
                }
                // revenue
                else if (request.getParameter("optionsRadios").equals("revenue"))
                {
                    String query;
                    PreparedStatement ps;
                    ResultSet res;
                    
                    // summary listing of revenue generated by particular flight
                    if (request.getParameter("revActions").equals("revByFlight"))
                    {
                        String airlineID = "AA";
                        String flightNo = "111";
                        /*String airlineID = request.getParameter("placeholder");
                        String flightNo = request.getParameter("placeholder");*/
                        
                        query = "DROP VIEW FlightRevenue";
                        ps = conn.prepareStatement(query);
                        ps.executeUpdate();
                        
                        query = "CREATE VIEW FlightRevenue(ResrNo, AirlineID, FlightNo, LegNo, Revenue) "
                                + "AS\n" +
                                "SELECT DISTINCT L.ResrNo, L.AirlineID, L.FlightNo, L.LegNo, "
                                + "R.TotalFare * 0.1\n" +
                                "FROM Reservation R, Legs L\n" +
                                "WHERE L.ResrNo = R.ResrNo\n" +
                                "GROUP BY L.ResrNo, L.AirlineID, L.FlightNo";
                        ps = conn.prepareStatement(query);
                        ps.executeUpdate();
                        
                        conn.commit();
                        
                        query = "SELECT AirlineID, FlightNo, SUM(Revenue) AS Revenue\n" +
                                "FROM FlightRevenue\n" +
                                "WHERE (AirlineID = ? AND FlightNo = ?)\n" +
                                "GROUP BY AirlineID, FlightNo";
                        ps = conn.prepareStatement(query);
                        ps.setString(1, airlineID);
                        ps.setInt(2, Integer.parseInt(flightNo));
                        res = ps.executeQuery();
                        
                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"airlineID\": \"" + res.getString("AirlineID") + "\","
                                + "\"flightNo\": \"" + res.getInt("FlightNo") + "\","
                                + "\"revenue\": \"" + res.getDouble("Revenue") + "\"},";
                        }

                        json = json.substring(0, json.length()-1);
                        json += "]}";

                        System.out.println(json);

                        out.print(json);
                    }
                    // summary listing of revenue generated by particular destination city
                    else if (request.getParameter("revActions").equals("revByDest"))
                    {
                        String destination = "London";
                        //String destination = request.getParameter("placeholder");
                        
                        query = "DROP VIEW FlightStops";
                        ps = conn.prepareStatement(query);
                        ps.executeUpdate();
                        
                        query = "CREATE VIEW FlightStops(AirlineID, FlightNo, NumStops) AS\n" +
                                "SELECT S.AirlineID, S.FlightNo, MAX(S.StopNo)\n" +
                                "FROM StopsAt S\n" +
                                "GROUP BY S.AirlineID, S.FlightNo";
                        ps = conn.prepareStatement(query);
                        ps.executeUpdate();
                        
                        conn.commit();
                        
                        query = "SELECT A.City, SUM(R.TotalFare * 0.1) AS Revenue\n" +
                                "FROM Airport A, Reservation R, StopsAt S, FlightStops F\n" +
                                "WHERE (S.AirlineID = F.AirlineID AND S.FlightNo = F.FlightNo\n" +
                                "AND S.StopNo = F.NumStops AND A.Id = S.AirportID\n" +
                                "AND A.City = ? AND R.ResrNo IN\n" +
                                    "(SELECT L.ResrNo\n" +
                                    "FROM Legs L\n" +
                                    "WHERE (L.AirlineID = S.AirlineID AND L.FlightNo = S.FlightNo)))";
                        ps = conn.prepareStatement(query);
                        ps.setString(1, destination);
                        res = ps.executeQuery();
                        
                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"city\": \"" + res.getString("City") + "\","
                                + "\"revenue\": \"" + res.getDouble("Revenue") + "\"},";
                        }

                        json = json.substring(0, json.length()-1);
                        json += "]}";

                        System.out.println(json);

                        out.print(json);
                    }
                    // summary listing of revenue generated by particular customer
                    else if (request.getParameter("revActions").equals("revByCust"))
                    {
                        String accountNo = "2001";
                        //String accountNo = request.getParameter("placeholder");
                        
                        query = "SELECT R.AccountNo, P.FirstName, P.LastName, SUM(R.TotalFare * 0.1) "
                                + "AS Revenue\n" +
                                "FROM Person P, Reservation R, Customer C\n" +
                                "WHERE (P.Id = C.Id AND R.AccountNo = C.AccountNo "
                                + "AND C.AccountNo = ?)";
                        ps = conn.prepareStatement(query);
                        ps.setInt(1, Integer.parseInt(accountNo));
                        res = ps.executeQuery();
                        
                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"accountNo\": \"" + res.getInt("AccountNo") + "\","
                                + "\"firstName\": \"" + res.getString("FirstName") + "\","
                                + "\"lastName\": \"" + res.getString("LastName") + "\","
                                + "\"revenue\": \"" + res.getDouble("Revenue") + "\"},";
                        }

                        json = json.substring(0, json.length()-1);
                        json += "]}";

                        System.out.println(json);

                        out.print(json);
                    }
                    // customer rep that generated most revenue
                    else if (request.getParameter("revActions").equals("custRepRev"))
                    {
                        query = "DROP VIEW CustRepRevenue";
                        ps = conn.prepareStatement(query);
                        ps.executeUpdate();

                        query = "CREATE VIEW CustRepRevenue(RepSSN, FirstName, LastName, TotalRevenue) AS\n" +
                                "SELECT R.RepSSN, P.FirstName, P.LastName, SUM(R.TotalFare * 0.1)\n" +
                                "FROM Reservation R, Person P, Employee E\n" +
                                "WHERE (R.RepSSN = E.SSN AND P.Id = E.Id)\n" +
                                "GROUP BY R.RepSSN, P.FirstName, P.LastName";
                        ps = conn.prepareStatement(query);
                        ps.executeUpdate();

                        conn.commit();

                        query = "SELECT C.RepSSN, C.FirstName, C.LastName, C.TotalRevenue\n" +
                                "FROM CustRepRevenue C\n" +
                                "WHERE C.TotalRevenue >= (SELECT MAX(C.TotalRevenue) FROM CustRepRevenue C)";
                        ps = conn.prepareStatement(query);
                        res = ps.executeQuery();

                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"repSSN\": \"" + res.getInt("RepSSN") + "\","
                                    + "\"firstName\": \"" + res.getString("FirstName") + "\","
                                    + "\"lastName\": \"" + res.getString("LastName") + "\","
                                    + "\"totalRevenue\": \"" + res.getDouble("TotalRevenue") + "\"},";
                        }

                        json = json.substring(0, json.length()-1);
                        json += "]}";

                        System.out.println(json);

                        out.print(json);
                    }
                    // customer that generated most revenue
                    else if(request.getParameter("revActions").equals("custRev"))
                    {
                        query = "DROP VIEW CustomerRevenue";
                        ps = conn.prepareStatement(query);
                        ps.executeUpdate();

                        query = "CREATE VIEW CustomerRevenue(AccountNo, FirstName, LastName, TotalRevenue)  "
                                + "AS\n" +
                                "SELECT R.AccountNo, P.FirstName, P.LastName, SUM(R.TotalFare * 0.1)\n" +
                                "FROM Reservation R, Person P, Customer C\n" +
                                "WHERE (R.AccountNo = C.AccountNo AND P.Id = C.Id)\n" +
                                "GROUP BY R.AccountNo, P.FirstName, P.LastName";
                        ps = conn.prepareStatement(query);
                        ps.executeUpdate();

                        conn.commit();

                        query = "SELECT C.AccountNo, C.FirstName, C.LastName, C.TotalRevenue\n" +
                                "FROM CustomerRevenue C\n" +
                                "WHERE C.TotalRevenue >= (SELECT MAX(C.TotalRevenue) FROM CustomerRevenue C)";
                        ps = conn.prepareStatement(query);
                        res = ps.executeQuery();

                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"accountNo\": \"" + res.getInt("AccountNo") + "\","
                                    + "\"firstName\": \"" + res.getString("FirstName") + "\","
                                    + "\"lastName\": \"" + res.getString("LastName") + "\","
                                    + "\"totalRevenue\": \"" + res.getDouble("TotalRevenue") + "\"},";
                        }

                        json = json.substring(0, json.length()-1);
                        json += "]}";

                        System.out.println(json);

                        out.print(json);
                    }
                }
                // list of customers who have seats reserved on given flight
                else if (request.getParameter("optionsRadios").equals("customers"))
                {
                        String airlineID = "AA";
                        String flightNo = "111";
                        /*String airlineID = request.getParameter("placeholder");
                        String flightNo = request.getParameter("placeholder");*/

                        String query = "SELECT DISTINCT R.AccountNo, R.Id, P.FirstName, P.LastName, "
                                       + "R.SeatNo\n" +
                                       "FROM ReservationPassenger R, Person P, Legs L\n" +
                                       "WHERE (R.Id = P.Id AND R.ResrNo = L.ResrNo AND L.AirlineID = ? "
                                       + "AND L.FlightNo = ?)";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, airlineID);
                        ps.setInt(2, Integer.parseInt(flightNo));
                        ResultSet res = ps.executeQuery();

                        json += "{\"data\":[";

                        while (res.next())
                        {
                            json += "{\"accountNo\": \"" + res.getInt("AccountNo") + "\","
                                    + "\"id\": \"" + res.getInt("Id") + "\","
                                    + "\"firstName\": \"" + res.getString("FirstName") + "\","
                                    + "\"lastName\": \"" + res.getString("LastName") + "\","
                                    + "\"seatNo\": \"" + res.getString("SeatNo") + "\"},";
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