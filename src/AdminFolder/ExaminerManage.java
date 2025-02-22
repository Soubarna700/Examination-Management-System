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

public class ExaminerManage 
{
    Connection con = Studentdb.getConnection();
    PreparedStatement ps;
    
    
    public int getMax()
    {
        int id = 0;
        Statement st;
        try {
            st= con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from examiner");
            while (rs.next())
            {                
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExaminerManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id+1;
    }
    public  void insert(int id,String name,String phone, String username, String password)
    {
        String sql = "insert into examiner(id,name,phoneno,username,password) values(?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.setString(4, username);
            ps.setString(5, password);
            
            if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(null, "New Exminer added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExaminerManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public  boolean isPhExists(String Phone)
    {
        try {
            ps = con.prepareStatement("select * from examiner where phoneno = ?");
            ps.setString(1, Phone);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExaminerManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    public  boolean isIdExists(int id)
    {
        try {
            ps = con.prepareStatement("select * from examiner where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExaminerManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    public  void getExaminerValue(JTable table,String searchValue)
    {
        String sql = "select * from examiner where concat(id,name,phoneno,username)like ? order by id desc";    
        //home er examinersearch txtfield e id name phone username diye search korle value debe table e password diye search korle debe na 
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+searchValue+"%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) 
            {                
                row = new Object[5];
                row[0]=rs.getInt(1);
                row[1]=rs.getString(2);
                row[2]=rs.getString(3);
                row[3]=rs.getString(4);
                row[4]=rs.getString(5);
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExaminerManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 //update students   
    public  void update(int id,String sname,String phoneno, String userName, String pass)
    {
        String sql = "update examiner set name=?,phoneno=?,username=?,password=? where id=?";
                    
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, sname);
            ps.setString(2, phoneno);
            ps.setString(3, userName);
            ps.setString(4, pass);
            
            ps.setInt(5, id);
            
            if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(null, "Examiner data updated successfully ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExaminerManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void delete(int id)
    {
       int yesOrNo = JOptionPane.showConfirmDialog(null, "Examiner records will also be delete ","Examiner delete",JOptionPane.OK_CANCEL_OPTION);
        if(yesOrNo==JOptionPane.OK_OPTION)
        {
           try {
               ps = con.prepareStatement("delete from examiner where id = ?");
               ps.setInt(1, id);
               if(ps.executeUpdate()>0)
               {
                   JOptionPane.showMessageDialog(null, "Examiner data delete successfully");
               }
           } catch (SQLException ex) {
               Logger.getLogger(ExaminerManage.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        }
    }
}
