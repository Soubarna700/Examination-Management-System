/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class Studentdb 
{
//    Connection conn=null;
    public static Connection getConnection()
    {
        String driverName = "com.mysql.cj.jdbc.Driver" ;
        String dbName = "studentdb" ;
        String url = "jdbc:mysql://localhost:3306/studentdb?zeroDateTimeBehavior=CONVERT_TO_NULL";
        
        String usr = "root" ;
        String pwd = "MySql@00" ;
        try
        {
            Class.forName(driverName) ;
            Connection conn = DriverManager.getConnection(url, usr, pwd) ;
            System.out.println("Database " + dbName + " connected successfully!");
            return conn;
//            JOptionPane.showMessageDialog(null, "Connection to database is successful");

        } catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Driver " + driverName + " not found");
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
