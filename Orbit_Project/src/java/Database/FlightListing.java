/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author RahulSarna
 */
public class FlightListing {
    
    
    public static void oneWayListings (String DepartureAirport, String ArrivalAirport, String DepartTime){
        
        RequestDispatcher dispatcher;
        String mysJDBCDriver = "com.mysql.jdbc.Driver";
        String mysURL = "jdbc:mysql://localhost:3306/cse_305_project_transactions?zeroDateTimeBehavior=convertToNull";
        String mysUserID = "root";
        String mysPassword = "";
        Connection conn=null;
        
        try{
                Class.forName(mysJDBCDriver).newInstance();
                Properties sysprops=System.getProperties();
                sysprops.put("user",mysUserID);
                sysprops.put("password",mysPassword);

                //connect to the database
                conn = DriverManager.getConnection(mysURL,sysprops);
                System.out.println("Connected successfully to database using JConnect");

                conn.setAutoCommit(false);
                
                String query = "SELECT * FROM Person";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet res = ps.executeQuery();
                
                
                query = "select *" +
                        "from stopsat a, stopsat b" +
                        "where a.AirlineID = b.AirlineID and a.StopNo < b.StopNo and a.AirportID=? and a.DeptTime like ?";
                
                ps = conn.prepareStatement(query);
                ps.setString(1, DepartureAirport);
                ps.setString(2, DepartTime);
                ps.executeUpdate();
                
                res = ps.executeQuery();
                
                System.out.println(res.next());
                
                
              }         
        catch(Exception e){
                e.printStackTrace();
        }  
        
        
    }
    
    
}
