/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import java.sql.*;
import javax.swing.*;

public class ConnectDAL {
    // kết nối với CSDL
    public static Connection getConnect()
    {
        try
        {
            String userName = "sa";
            String password = "123456789";
            String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=QLSV1C";
            return DriverManager.getConnection(connectionURL,userName,password);
        }
        catch (Exception e)
        {
           // JOptionPane.showMessageDialog(null, "kết nối thất bại", "Thông Báo", 1);
            
            return null;
        }
    }
    
}
