/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UserDetails;

import com.mysql.cj.protocol.Resultset;
import DatabaseConnection.Studentdb;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class UserExam extends javax.swing.JFrame {

    Connection con = Studentdb.getConnection();
    PreparedStatement ps;

    public String questionId = "1";
    public String answer;
    public int min = 0;
    public int sec = 0;
    public int marks = 0;

    Timer time;

    public UserExam() {
        initComponents();
    }

    public UserExam(String roll) {
        initComponents();
        lblRoll.setText(roll);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        lblDate.setText(sdf.format(date));

        try {
            ps = con.prepareStatement("select * from studentresult where roll = ?");
            ps.setString(1, roll);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String stuName = rs.getString(2);
                lblName.setText(stuName);
            }

            ps = con.prepareStatement("select * from question where id = ?");
            ps.setString(1, questionId);

            ResultSet qrs = ps.executeQuery();
            while (qrs.next()) {
                lblQsNo.setText(qrs.getString(1));
                lblQsName.setText(qrs.getString(2));
                jRadioButton5.setText(qrs.getString(3));
                jRadioButton6.setText(qrs.getString(4));
                jRadioButton7.setText(qrs.getString(5));
                jRadioButton8.setText(qrs.getString(6));

                answer = qrs.getString(7);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserExam.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }

        // time program
        setLocationRelativeTo(this);
        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTimeRunning1Sec.setText(String.valueOf(sec));
                lblTimeRunningMin.setText(String.valueOf(min));
                if (sec == 60) {
                    sec = 0;
                    min++;
                    if (min == 10) {
                        time.stop();
                        answerCheck();
                        submit();
                    }
                }
                sec++;
            }
        });
        time.start();

    }

    public void answerCheck() {
        String studentAns = "";
        if (jRadioButton5.isSelected()) {
            studentAns = jRadioButton5.getText();
        } else if (jRadioButton6.isSelected()) {
            studentAns = jRadioButton6.getText();
        } else if (jRadioButton7.isSelected()) {
            studentAns = jRadioButton7.getText();
        } else {
            studentAns = jRadioButton8.getText();
        }

        // student ans check with database answer !!
        if (studentAns.equals(answer)) {
            marks = marks + 1;
            String marksstring = String.valueOf(marks);
            lblMarks.setText(marksstring);
        }

        //question no change
        int questionIdint = Integer.parseInt(this.questionId);
        questionIdint = questionIdint + 1;
        questionId = String.valueOf(questionIdint);
        
        //clear jradiobutton
        jRadioButton5.setSelected(false);
        jRadioButton6.setSelected(false);
        jRadioButton7.setSelected(false);
        jRadioButton8.setSelected(false);
        

        //last question hide next button
        if (questionId.equals("10")) {
            btnNext.setVisible(false);
        }
    }

    public void question() {
        try {

            ps = con.prepareStatement("select * from question where id = ?");
            ps.setString(1, questionId);

            ResultSet qrs = ps.executeQuery();
            while (qrs.next()) {
                lblQsNo.setText(qrs.getString(1));
                lblQsName.setText(qrs.getString(2));
                jRadioButton5.setText(qrs.getString(3));
                jRadioButton6.setText(qrs.getString(4));
                jRadioButton7.setText(qrs.getString(5));
                jRadioButton8.setText(qrs.getString(6));

                answer = qrs.getString(7);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserExam.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void submit() {
        String rollNo = lblRoll.getText();
        answerCheck();
        
        try {
            ps = con.prepareStatement("update studentresult set marks=? where roll=?");
            System.out.println("Marks are : "+marks);
            ps.setInt(1, marks);
            ps.setString(2, rollNo);
            
            
            String smarks = String.valueOf("marks :"+marks+"Roll no :"+rollNo);
            JOptionPane.showMessageDialog(null, "Your score is : "+smarks);
            
            if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(null, "result data updated successfully ");
                
            }
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserExam.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTotalTime = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblTimeRunningMin = new javax.swing.JLabel();
        lblTimeRunning1Sec = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblRoll = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTotalQs = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblQsNo = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblMarks = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblQsName = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        lblTotalTime.setBackground(new java.awt.Color(153, 255, 153));
        lblTotalTime.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 51), 4, true));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setText("WLCOME TO SM EXAMINATION");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Date :");

        lblDate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblDate.setText("jLabel13");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("Total Time :");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("Time Taken :");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel16.setText("10 Min");

        lblTimeRunningMin.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblTimeRunningMin.setForeground(new java.awt.Color(255, 51, 0));
        lblTimeRunningMin.setText("00");

        lblTimeRunning1Sec.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblTimeRunning1Sec.setForeground(new java.awt.Color(255, 51, 0));
        lblTimeRunning1Sec.setText("00");

        javax.swing.GroupLayout lblTotalTimeLayout = new javax.swing.GroupLayout(lblTotalTime);
        lblTotalTime.setLayout(lblTotalTimeLayout);
        lblTotalTimeLayout.setHorizontalGroup(
            lblTotalTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblTotalTimeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addComponent(jLabel12)
                .addGap(60, 60, 60)
                .addComponent(lblDate)
                .addGap(134, 134, 134)
                .addGroup(lblTotalTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(27, 27, 27)
                .addGroup(lblTotalTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(lblTotalTimeLayout.createSequentialGroup()
                        .addComponent(lblTimeRunningMin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTimeRunning1Sec)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        lblTotalTimeLayout.setVerticalGroup(
            lblTotalTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblTotalTimeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lblTotalTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lblTotalTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(lblTotalTimeLayout.createSequentialGroup()
                            .addGroup(lblTotalTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(jLabel16))
                            .addGap(18, 18, 18)
                            .addGroup(lblTotalTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTimeRunningMin)
                                .addComponent(lblTimeRunning1Sec)))
                        .addGroup(lblTotalTimeLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(lblTotalTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(lblDate)))
                        .addGroup(lblTotalTimeLayout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jLabel15))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 255, 153));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 51), 4, true));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Name :");

        lblName.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblName.setText("Soubarna Mahinder");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Roll :");

        lblRoll.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblRoll.setText("10000000");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Total Question :");

        lblTotalQs.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblTotalQs.setText("100");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel8.setText("Question No :");

        lblQsNo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblQsNo.setText("000");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Marks :");

        lblMarks.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblMarks.setText("000");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMarks)
                    .addComponent(lblQsNo)
                    .addComponent(lblTotalQs)
                    .addComponent(lblRoll)
                    .addComponent(lblName))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblName))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblRoll))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTotalQs))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblQsNo))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblMarks))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 255, 153));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 51), 4, true));

        lblQsName.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblQsName.setText("Question Demo ?");

        btnNext.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnSubmit.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jRadioButton5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jRadioButton5.setText("jRadioButton5");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        jRadioButton6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jRadioButton6.setText("jRadioButton6");
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });

        jRadioButton7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jRadioButton7.setText("jRadioButton7");
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });

        jRadioButton8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jRadioButton8.setText("jRadioButton8");
        jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton8)
                    .addComponent(jRadioButton7)
                    .addComponent(jRadioButton6)
                    .addComponent(jRadioButton5)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNext)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblQsName)
                                .addGap(4, 4, 4)))
                        .addGap(118, 118, 118)
                        .addComponent(btnSubmit)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblQsName)
                .addGap(28, 28, 28)
                .addComponent(jRadioButton5)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton6)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton7)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton8)
                .addGap(74, 74, 74)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnSubmit))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTotalTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblTotalTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        
        answerCheck();
        question();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        
        int a = JOptionPane.showConfirmDialog(null, "Do you really want to submit","Select",JOptionPane.YES_NO_OPTION);
        if(a==0){
            answerCheck();
            submit();
            UserHome uh = new UserHome("9674761459");
            uh.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton5.isSelected()){
            jRadioButton6.setSelected(false);
            jRadioButton7.setSelected(false);
            jRadioButton8.setSelected(false);
            
        }
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton6.isSelected())
        {
            jRadioButton5.setSelected(false);
            jRadioButton8.setSelected(false);
            jRadioButton7.setSelected(false);
        }
    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton7ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton7.isSelected())
        {
            jRadioButton6.setSelected(false);
            jRadioButton5.setSelected(false);
            jRadioButton8.setSelected(false);
        }
    }//GEN-LAST:event_jRadioButton7ActionPerformed

    private void jRadioButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton8ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton8.isSelected())
        {
            jRadioButton6.setSelected(false);
            jRadioButton7.setSelected(false);
            jRadioButton5.setSelected(false);
        }
    }//GEN-LAST:event_jRadioButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new UserExam().setVisible(true);
                new UserExam("123456").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblMarks;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblQsName;
    private javax.swing.JLabel lblQsNo;
    private javax.swing.JLabel lblRoll;
    private javax.swing.JLabel lblTimeRunning1Sec;
    private javax.swing.JLabel lblTimeRunningMin;
    private javax.swing.JLabel lblTotalQs;
    private javax.swing.JPanel lblTotalTime;
    // End of variables declaration//GEN-END:variables
}
