/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Examiner;

import AdminFolder.Student;
import DatabaseConnection.Studentdb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class Question 
{
    Connection con = Studentdb.getConnection();
    PreparedStatement ps;
    
    public int getMax()
    {
        int id = 0;
        Statement st;
        try {
            st= con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from question");
            while (rs.next())
            {                
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id+1;
    }
    
    
    public  void insert(int id,String qsName, String option1,String option2,String option3,String option4,String answer )
    {
        String sql = "insert into question values(?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, qsName);
            ps.setString(3, option1);
            ps.setString(4, option2);
            ps.setString(5, option3);
            ps.setString(6, option4);
            ps.setString(7, answer);
            
            if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(null, "New Question added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public  boolean isQuestionExists(String question)
    {
        try {
            ps = con.prepareStatement("select * from question where question = ?");
            ps.setString(1, question);
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
            ps = con.prepareStatement("select * from question where id = ?");
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
    
    public  void getQuestionValue(JTable table,String searchValue)
    {
        String sql = "select * from question where concat(id,question,answer)like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+searchValue+"%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) 
            {                
                row = new Object[7];
                row[0]=rs.getInt(1);      // id
                row[1]=rs.getString(2);    // question
                row[2]=rs.getString(3);     // optioan a
                row[3]=rs.getString(4);      // optionb
                row[4]=rs.getString(5);     //optionc
                row[5]=rs.getString(6);      //optiond
                row[6]=rs.getString(7);      //answer
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  void update(int id,String qsName, String option1,String option2,String option3,String option4,String answer )
    {
        String sql = "update question set question=?,optiona=?,optionb=?,optionc=?,optiond=?,answer=? where id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, qsName);
            ps.setString(2, option1);
            ps.setString(3, option2);
            ps.setString(4, option3);
            ps.setString(5, option4);
            ps.setString(6, answer);
            ps.setInt(7, id);

            
            if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(null, "Question updated successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void delete(int id)
    {
       int yesOrNo = JOptionPane.showConfirmDialog(null, "Question and answers will also be delete ","Question delete",JOptionPane.OK_CANCEL_OPTION);
        if(yesOrNo==JOptionPane.OK_OPTION)
        {
           try {
               ps = con.prepareStatement("delete from question where id = ?");
               ps.setInt(1, id);
               if(ps.executeUpdate()>0)
               {
                   JOptionPane.showMessageDialog(null, "Question delete successfully");
               }
           } catch (SQLException ex) {
               Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        }
    }
}
