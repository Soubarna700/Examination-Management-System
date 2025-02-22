/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminFolder;

import DatabaseConnection.Studentdb;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Student 
{
    Connection con = Studentdb.getConnection();
    PreparedStatement ps;
    
    
    public int getMax()
    {
        int id = 0;
        Statement st;
        try {
            st= con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from studentdata");
            while (rs.next())
            {                
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id+1;
    }
    public  void insert(int id,String sname,String dtdob, String gender, String email,String phone,String father,
            String address,String marks,String dtApply,String dtQual,String qualId,String zone,String imagePath)
    {
        String sql = "insert into studentdata values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, sname);
            ps.setString(3, dtdob);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, father);
            ps.setString(8, address);
            ps.setString(9, dtApply);
            ps.setString(10, qualId);
            ps.setString(11, marks);
            ps.setString(12, zone);
            ps.setString(13, imagePath);
            ps.setString(14, dtQual);
            
            if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(null, "New student added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public  boolean isEmailExists(String email)
    {
        try {
            ps = con.prepareStatement("select * from studentdata where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    public  boolean isPhExists(String Phone)
    {
        try {
            ps = con.prepareStatement("select * from studentdata where phone = ?");
            ps.setString(1, Phone);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    public  boolean isIdExists(int id)
    {
        try {
            ps = con.prepareStatement("select * from studentdata where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    public  void getStudentValue(JTable table,String searchValue)
    {
        String sql = "select * from studentdata where concat(id,name,email,phone)like ? order by id desc";    
        //home we search txtfield e id name email phone diye search korle value debe table e  gender dob esb diye search korle debe na 
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+searchValue+"%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) 
            {                
                row = new Object[9];
                row[0]=rs.getInt(1);
                row[1]=rs.getString(2);
                row[2]=rs.getString(3);
                row[3]=rs.getString(4);
                row[4]=rs.getString(5);
                row[5]=rs.getString(6);
                row[6]=rs.getString(9);
                row[7]=rs.getString(13);
                row[8]=rs.getString(10);
//                row[0]=rs.getInt(1);
//                row[0]=rs.getInt(1);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 //update students   
    public  void update(int id,String sname,String dtdob, String gender, String email,String phone,String father,
            String address,String marks,String dtApply,String dtQual,String qualId,String zone,String imagePath)
    {
        String sql = "update studentdata set name=?,dob=?,gender=?,email=?,phone=?,guardian=?,address=?,"
                + "application_date=?,qualificationid=?,marks=?,examZone=?,image_path=?,QualDate=? where id=?";     
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, sname);
            ps.setString(2, dtdob);
            ps.setString(3, gender);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, father);
            ps.setString(7, address);
            ps.setString(8, dtApply);
            ps.setString(9, qualId);
            ps.setString(10, marks);
            ps.setString(11, zone);
            ps.setString(12, imagePath);
            ps.setString(13, dtQual);
            ps.setInt(14, id);
            
            if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(null, "Student data updated successfully ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void delete(int id)
    {
       int yesOrNo = JOptionPane.showConfirmDialog(null, "Course and records will also be delete ","Student delete",JOptionPane.OK_CANCEL_OPTION);
        if(yesOrNo==JOptionPane.OK_OPTION)
        {
           try {
               ps = con.prepareStatement("delete from studentdata where id = ?");
               ps.setInt(1, id);
               if(ps.executeUpdate()>0)
               {
                   JOptionPane.showMessageDialog(null, "Student data delete successfully");
               }
           } catch (SQLException ex) {
               Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        }
    }
}
