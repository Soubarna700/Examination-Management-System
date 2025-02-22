/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package AdminFolder;

import com.toedter.calendar.JDateChooser;
import DatabaseConnection.Studentdb;
import java.awt.Color;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Home extends javax.swing.JFrame {

    Student student = new Student();
    ExaminerManage examiner = new ExaminerManage();
    private DefaultTableModel model;
    private DefaultTableModel modelExaminer;
    private String imagePath;
    private int rowIndex;
    private int rowIndexExaminer;
    private String adminUsername;
    private String adminPass;
    private int adminId;

    // amar nijer kora gulo er niche ache 
    Connection con = Studentdb.getConnection();
    PreparedStatement ps;
//    ResultSet rs;

    public Home() {
        initComponents();
        init();
        fillAdminData();
    }
    public Home(String adminUserName, String adminPassWord) {
        initComponents();
        init();
        adminUsername = adminUserName;
        adminPass = adminPassWord;
        fillAdminData();
    }

    @SuppressWarnings("unchecked")

    public void init() {
        tableViewStudent();
        tableViewExaminer();
        txtID.setText(String.valueOf(student.getMax()));
        txtExaminerId.setText(String.valueOf(examiner.getMax()));
    }

    private void tableViewStudent() {
        student.getStudentValue(jTable1, "");
        model = (DefaultTableModel) jTable1.getModel();
        jTable1.setRowHeight(30);
        jTable1.setShowGrid(true);
        jTable1.setGridColor(Color.black);
        jTable1.setBackground(Color.white);
    }
    
    private void tableViewExaminer() {
        examiner.getExaminerValue(jTable2, "");
        modelExaminer = (DefaultTableModel) jTable2.getModel();
        jTable2.setRowHeight(30);
        jTable2.setShowGrid(true);
        jTable2.setGridColor(Color.black);
        jTable2.setBackground(Color.white);
    }

    private void clearStudent() {
        txtID.setText(String.valueOf(student.getMax()));

        txtID.setText(null);
        txtName.setText(null);
        txtPhNo.setText(null);
        txtEmail.setText(null);
        txtFatherName.setText(null);
        txtAddress.setText(null);
        txtMark.setText(null);
        datePickerDob.setDate(null);
        datePickerApplication.setDate(null);
        datePickerQual.setDate(null);
        txtComBoxGender.setSelectedIndex(0);
        txtComboBoxQual.setSelectedIndex(0);
        txtComboBoxZone.setSelectedIndex(0);
        lblPicImg.setIcon(null);
        jTable1.clearSelection();
        imagePath = null;
    }

    private void clearExaminer() {
        txtExaminerId.setText(String.valueOf(examiner.getMax()));

        txtExaminerId.setText(null);
        txtExaminerName.setText(null);
        txtExaminerPhNo.setText(null);
        txtExaminerUsername.setText(null);
        txtExaminerPass.setText(null);

    }

    public boolean isEmptyStudent() {
        if (txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student name is missing");
            return false;
        }

// date e er jinispati        
        if (datePickerDob.getDate() == null) // amr date calender ta txt type mne string type diye dey ekebare
        {
//            System.out.println(datePickerDob.toString());
//            System.out.println(datePickerDob.getda());
            JOptionPane.showMessageDialog(this, "Student Date of birth is missing");
            return false;
        }
        if (datePickerApplication.getDate() == null) // amr date calender ta txt type mne string type diye dey ekebare
        {
            JOptionPane.showMessageDialog(this, "Studnt application date is missing");
            return false;
        }
        if (datePickerQual.getDate() == null) // amr date calender ta txt type mne string type diye dey ekebare
        {
            JOptionPane.showMessageDialog(this, "Student qualification date is missing");
            return false;
        }
        if (datePickerDob.getDate().compareTo(new Date()) > 0) {
            JOptionPane.showMessageDialog(this, "No students from the future are allowed");
            return false;
        }
        if (datePickerApplication.getDate().compareTo(new Date()) > 0) {
            JOptionPane.showMessageDialog(this, "Apply date from the future are not allowed");
            return false;
        }
        if (datePickerQual.getDate().compareTo(new Date()) > 0) {
            JOptionPane.showMessageDialog(this, "previous qualification from the future are not allowed");
            return false;
        }
        if (txtPhNo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student Phone no is missing");
            return false;
        }
        if (txtPhNo.getText().length() >= 15) {
            JOptionPane.showMessageDialog(this, "Phone no is too long");
            return false;
        }
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student email is missing");
            return false;
        }
        if (txtEmail.getText().matches("^.+@.\\..+$")) // valid email check problem ache code e 
        {
            JOptionPane.showMessageDialog(this, "Invalid email");
            return false;
        }
        if (txtFatherName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student Father name is missing");
            return false;
        }
        if (txtAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student address is missing");
            return false;
        }
        if (txtMark.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student marks is missing");
            return false;
        }
        if (imagePath == null) {
            JOptionPane.showMessageDialog(this, "Please add your image");
            return false;
        }
        return true;
    }

    public boolean isEmptyExaminer() {
        if (txtExaminerName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Examiner name is missing");
            return false;
        }
        if (txtExaminerPhNo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Examiner phone no is missing");
            return false;
        }
        if (txtExaminerUsername.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Examiner Username is missing");
            return false;
        }
        if (txtExaminerPass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Examiner password is missing");
            return false;
        }
        return true;
    }

    private Date convertStringToDate(String sdate) {
        //      String dobDate = datePickerDob.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date dt = null;
        try {
            Date dtdob = sdf.parse(sdate);          //exception throw kore 
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dt;

    }

    public boolean check() {
        String newEmail = txtEmail.getText();
        String newph = txtPhNo.getText();
        String oldEmail = model.getValueAt(rowIndex, 4).toString();
        String oldPh = model.getValueAt(rowIndex, 5).toString();
        if (newEmail.equals(oldEmail) && newph.equals(oldPh)) {
            return false;
        } else {
            if (!newEmail.equals(oldEmail)) {

                boolean x = student.isEmailExists(newEmail);
                if (x) {
                    JOptionPane.showMessageDialog(this, "This email already exits");
                }
                return x;
            }
            if (!newph.equals(oldPh)) {

                boolean x = student.isPhExists(newph);
                if (x) {
                    JOptionPane.showMessageDialog(this, "This phone already exits");
                }
                return x;
            }

        }
        return false;
    }
    
    public boolean  checkExaminer()
    {
       String newPh = txtExaminerPhNo.getText();
       String oldPh = modelExaminer.getValueAt(rowIndex, 2).toString();
       if(newPh.equals(oldPh))
       {
           return false;
       }else{
           if(newPh.equals(oldPh)){
               boolean x = examiner.isPhExists(newPh);
               if(x)
                   JOptionPane.showMessageDialog(this, "This question already exits");
           }
       }
       return false;
    }

    public void fillAdminData() {

        try {
            ps = con.prepareStatement("select * from admin where username = ? and password = ?");
            ps.setString(1, adminUsername);
            ps.setString(2, adminPass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                adminId = rs.getInt(1);
                txtAdminId.setText(adminId + "");
                txtAdminUserName.setText(rs.getString(2));
                txtAdminPassword.setText(rs.getString(3));
                txtAdminName.setText(rs.getString(4));
                txtAdminScQS.setText(rs.getString(5));
                txtAdminScAns.setText(rs.getString(6));

            } else {
                JOptionPane.showMessageDialog(this, "something Wrong in fillAdmindata function");

            }

        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhNo = new javax.swing.JTextField();
        txtFatherName = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtMark = new javax.swing.JTextField();
        txtComBoxGender = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtComboBoxQual = new javax.swing.JComboBox<>();
        txtComboBoxZone = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblPicImg = new javax.swing.JLabel();
        btnpic = new javax.swing.JButton();
        datePickerDob = new com.toedter.calendar.JDateChooser();
        datePickerApplication = new com.toedter.calendar.JDateChooser();
        datePickerQual = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        txtExaminerId = new javax.swing.JTextField();
        txtExaminerName = new javax.swing.JTextField();
        txtExaminerUsername = new javax.swing.JTextField();
        txtExaminerPhNo = new javax.swing.JTextField();
        txtExaminerPass = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        txtSearchExaminer = new javax.swing.JTextField();
        btxSearchExaminer = new javax.swing.JButton();
        btnRefreshExaminer = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        btnExaminerUpdate = new javax.swing.JButton();
        btnExaminerPrint = new javax.swing.JButton();
        btnExaminerClear = new javax.swing.JButton();
        btnExaminerLogOut = new javax.swing.JButton();
        btnExaminerAdd = new javax.swing.JButton();
        btnExaminerDelete = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        txtAdminId = new javax.swing.JTextField();
        txtAdminName = new javax.swing.JTextField();
        txtAdminUserName = new javax.swing.JTextField();
        txtAdminPassword = new javax.swing.JTextField();
        txtAdminScAns = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        btnAdminClear = new javax.swing.JButton();
        btnAdminLogout = new javax.swing.JButton();
        btnAdminUpdate = new javax.swing.JButton();
        txtAdminOldPass = new javax.swing.JPasswordField();
        jLabel35 = new javax.swing.JLabel();
        txtAdminScQS = new javax.swing.JTextField();
        btnForget = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 255, 102));

        jPanel2.setBackground(new java.awt.Color(102, 204, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 40)); // NOI18N
        jLabel1.setText("STUDENTS MANAGEMENT");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(532, 593));

        jPanel3.setBackground(new java.awt.Color(102, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 255, 0));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 51), 4, true));

        txtID.setEditable(false);
        txtID.setBackground(new java.awt.Color(204, 204, 204));

        txtPhNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhNoKeyTyped(evt);
            }
        });

        txtMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarkActionPerformed(evt);
            }
        });

        txtComBoxGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "Others" }));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Student's ID");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Phone Number");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Father's Name");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Address");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Application Date");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Date of birth");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Name");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Gender");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Email");

        txtComboBoxQual.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Matriculation", "Hs", "Graduation", "Diploma" }));

        txtComboBoxZone.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Howrah", "Kolkata", "Hoogly", "Siliguri" }));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Marks");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Exam Zone");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Qualification Date");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("Qualification ID");

        jPanel6.setBackground(new java.awt.Color(0, 255, 51));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 0), 4, true));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPicImg, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPicImg, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        btnpic.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnpic.setText("Choose Picture");
        btnpic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpicActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnpic)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(btnpic)))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        datePickerDob.setDateFormatString("yyyy-MM-dd");

        datePickerApplication.setDateFormatString("yyyy-MM-dd");

        datePickerQual.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtComboBoxZone, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtPhNo)
                                    .addComponent(txtFatherName)
                                    .addComponent(txtAddress)
                                    .addComponent(txtMark)
                                    .addComponent(txtID)
                                    .addComponent(datePickerDob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(datePickerApplication, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtComBoxGender, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtComboBoxQual, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 31, Short.MAX_VALUE))
                                    .addComponent(datePickerQual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(datePickerDob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComBoxGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFatherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(datePickerApplication, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComboBoxQual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMark, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(datePickerQual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComboBoxZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jPanel5.setBackground(new java.awt.Color(102, 255, 102));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 4, true));

        jPanel7.setBackground(new java.awt.Color(153, 255, 153));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 153, 0), 4, true));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel15.setText("Search Students");

        btnSearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnRefresh))
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(51, 255, 102));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 0), 4, true));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Id", "Name", "DOB", "Gender", "Email", "PhoneNo", "Application Date", "Image Path", "Qualification"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel11.setBackground(new java.awt.Color(102, 255, 102));
        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 204, 0), 4, true));

        btnAdd.setBackground(new java.awt.Color(0, 204, 204));
        btnAdd.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAdd.setText("Add New");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 204, 204));
        btnUpdate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnPrint.setBackground(new java.awt.Color(0, 204, 204));
        btnPrint.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(0, 204, 204));
        btnDelete.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 204, 204));
        btnClear.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnLogOut.setBackground(new java.awt.Color(0, 204, 204));
        btnLogOut.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLogOut.setText("Log Out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 132, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Student Manage", jPanel3);

        jPanel12.setBackground(new java.awt.Color(102, 255, 255));

        jPanel13.setBackground(new java.awt.Color(0, 255, 0));
        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 51), 4, true));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setText("Examiner's ID");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setText("Phone Number");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setText("Password");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel22.setText("Name");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel24.setText("Username");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtExaminerUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtExaminerName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                    .addComponent(txtExaminerPass)
                                    .addComponent(txtExaminerId))
                                .addContainerGap())))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(txtExaminerPhNo)
                        .addContainerGap())))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtExaminerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(15, 15, 15)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExaminerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExaminerPhNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExaminerUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtExaminerPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(421, 421, 421))
        );

        jPanel17.setBackground(new java.awt.Color(102, 255, 102));
        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 4, true));

        jPanel18.setBackground(new java.awt.Color(153, 255, 153));
        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 153, 0), 4, true));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel29.setText("Search Examiner");

        btxSearchExaminer.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btxSearchExaminer.setText("Search");
        btxSearchExaminer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btxSearchExaminerActionPerformed(evt);
            }
        });

        btnRefreshExaminer.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnRefreshExaminer.setText("Refresh");
        btnRefreshExaminer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshExaminerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchExaminer, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(btxSearchExaminer, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefreshExaminer, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(txtSearchExaminer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btxSearchExaminer)
                    .addComponent(btnRefreshExaminer))
                .addContainerGap())
        );

        jPanel19.setBackground(new java.awt.Color(51, 255, 102));
        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 0), 4, true));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Examiner Id", "Name", "PhoneNo", "Username", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel20.setBackground(new java.awt.Color(102, 255, 102));
        jPanel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 204, 0), 4, true));

        btnExaminerUpdate.setBackground(new java.awt.Color(0, 204, 204));
        btnExaminerUpdate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnExaminerUpdate.setText("Update");
        btnExaminerUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminerUpdateActionPerformed(evt);
            }
        });

        btnExaminerPrint.setBackground(new java.awt.Color(0, 204, 204));
        btnExaminerPrint.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnExaminerPrint.setText("Print");
        btnExaminerPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminerPrintActionPerformed(evt);
            }
        });

        btnExaminerClear.setBackground(new java.awt.Color(0, 204, 204));
        btnExaminerClear.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnExaminerClear.setText("Clear");
        btnExaminerClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminerClearActionPerformed(evt);
            }
        });

        btnExaminerLogOut.setBackground(new java.awt.Color(0, 204, 204));
        btnExaminerLogOut.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnExaminerLogOut.setText("Log Out");
        btnExaminerLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminerLogOutActionPerformed(evt);
            }
        });

        btnExaminerAdd.setBackground(new java.awt.Color(0, 204, 204));
        btnExaminerAdd.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnExaminerAdd.setText("Add New");
        btnExaminerAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminerAddActionPerformed(evt);
            }
        });

        btnExaminerDelete.setBackground(new java.awt.Color(0, 204, 204));
        btnExaminerDelete.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnExaminerDelete.setText("Delete");
        btnExaminerDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminerDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExaminerAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExaminerUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExaminerDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(btnExaminerPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExaminerClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnExaminerLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExaminerUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExaminerPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExaminerClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExaminerLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExaminerAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExaminerDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 797, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Examiner Manage", jPanel12);

        jPanel14.setBackground(new java.awt.Color(102, 255, 255));

        jPanel15.setBackground(new java.awt.Color(0, 255, 0));
        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 51), 4, true));

        txtAdminId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAdminIdKeyTyped(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel30.setText("ID");

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel32.setText("Password");

        jLabel33.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel33.setText("Security Question");

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel34.setText("Security Answer");

        jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel37.setText("Name");

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel39.setText("Username");

        btnAdminClear.setBackground(new java.awt.Color(0, 204, 204));
        btnAdminClear.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAdminClear.setText("Clear");
        btnAdminClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminClearActionPerformed(evt);
            }
        });

        btnAdminLogout.setBackground(new java.awt.Color(0, 204, 204));
        btnAdminLogout.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAdminLogout.setText("Log Out");
        btnAdminLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminLogoutActionPerformed(evt);
            }
        });

        btnAdminUpdate.setBackground(new java.awt.Color(0, 204, 204));
        btnAdminUpdate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAdminUpdate.setText("Update");
        btnAdminUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminUpdateActionPerformed(evt);
            }
        });

        txtAdminOldPass.setText("jPasswordField1");

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel35.setText("Old Password");

        txtAdminScQS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdminScQSActionPerformed(evt);
            }
        });
        txtAdminScQS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAdminScQSKeyTyped(evt);
            }
        });

        btnForget.setBackground(new java.awt.Color(0, 153, 153));
        btnForget.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnForget.setText("Forget Password");
        btnForget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForgetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(btnAdminClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(btnAdminLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAdminName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(txtAdminPassword)
                            .addComponent(txtAdminScAns)
                            .addComponent(txtAdminId)
                            .addComponent(txtAdminUserName)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(btnAdminUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForget))
                                    .addComponent(txtAdminOldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtAdminScQS))
                        .addContainerGap())))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAdminId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdminName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdminUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(txtAdminScQS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdminScAns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(54, 54, 54)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdminLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdminClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdminOldPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(43, 43, 43)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdminUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnForget, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(152, 152, 152))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Update Security Details", jPanel14);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1343, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 67, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1379, 794));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarkActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        MessageFormat header = new MessageFormat("Student information");
        MessageFormat footer = new MessageFormat("Page(0,number,integer)");
        try {
            jTable1.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        if (isEmptyStudent()) {
            if (!student.isEmailExists(txtEmail.getText())) {
                if (!student.isPhExists(txtPhNo.getText())) {
                    int id = student.getMax();
                    String name = txtName.getText();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateDob = sdf.format(datePickerDob.getDate());
                    String dateApply = sdf.format(datePickerApplication.getDate());
                    String dateQual = sdf.format(datePickerQual.getDate());
                    String gender = txtComBoxGender.getSelectedItem().toString();
                    String qualId = txtComboBoxQual.getSelectedItem().toString();
                    String zone = txtComboBoxZone.getSelectedItem().toString();
                    String email = txtEmail.getText();
                    String phNo = txtPhNo.getText();
                    String address = txtAddress.getText();
                    String fatherName = txtFatherName.getText();
                    String marks = txtMark.getText();
                    student.insert(id, name, dateDob, gender, email, phNo, fatherName, address,
                            marks, dateApply, dateQual, qualId, zone, imagePath);

                    jTable1.setModel(new DefaultTableModel(null, new Object[]{"Student Id", "Name", "DOB",
                        "Gender", "Email", "PhoneNo", "Application Date", "Image Path", "Qualification"}));
                    student.getStudentValue(jTable1, "");//   
                    clearStudent();
                } else {
                    JOptionPane.showMessageDialog(this, "This Phone no already exits");
                }
            } else {
                JOptionPane.showMessageDialog(this, "This mail already exits");
            }
        }


    }//GEN-LAST:event_btnAddActionPerformed

    private void btnExaminerAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminerAddActionPerformed
        // TODO add your handling code here:

        if (isEmptyExaminer()) {
            if (!examiner.isPhExists(txtExaminerPhNo.getText())) 
            {

                int id = examiner.getMax();
                String exname = txtExaminerName.getText();
                String exPhno = txtExaminerPhNo.getText();
                String exUserName = txtExaminerUsername.getText();
                String exPass = txtExaminerPass.getText();
                examiner.insert(id, exname, exPhno, exUserName, exPass);

                jTable2.setModel(new DefaultTableModel(null, new Object[]{"Examiner Id", "Name", "PhoneNo","Username","Password"}));
                examiner.getExaminerValue(jTable2, "");//   
                clearExaminer();

            }
            else{
                JOptionPane.showMessageDialog(this, "This phone no already exists");
            }
        }
    }//GEN-LAST:event_btnExaminerAddActionPerformed

    private void btnExaminerPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminerPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExaminerPrintActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(this, "Do you want to log out now ?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0)
        {
            MainPage mp = new MainPage();
            mp.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed

        // TODO add your handling code here:
        clearStudent();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtPhNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhNoKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPhNoKeyTyped

    private void btnpicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpicActionPerformed
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".image", "jpg", "gif", "png");
        file.addChoosableFileFilter(filter);
        int output = file.showSaveDialog(file);
        if (output == JFileChooser.APPROVE_OPTION) {
            File selectFile = file.getSelectedFile();
            String path = selectFile.getAbsolutePath();
            lblPicImg.setIcon(imageAdjust(path, null));
            imagePath = path;
        } else {
            JOptionPane.showMessageDialog(this, "No imag selectedd");
        }
    }//GEN-LAST:event_btnpicActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        model = (DefaultTableModel) jTable1.getModel();
        rowIndex = jTable1.getSelectedRow();
        txtID.setText(model.getValueAt(rowIndex, 0).toString());
        txtName.setText(model.getValueAt(rowIndex, 1).toString());
        String id = model.getValueAt(rowIndex, 0).toString();
        System.out.println(id);

        // amar lekha code    
        try {
            ps = con.prepareStatement("select * from studentdata where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int git = rs.getInt(1);
                txtID.setText(git + "");    // string banalam
                txtName.setText(rs.getString(2));

                String sDateDob = rs.getString(3);
                System.out.println(sDateDob);
                Date dt = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dt = sdf.parse(sDateDob);
                } catch (ParseException ex) {
                    System.out.println("problem in 2nd try catch");
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }
                datePickerDob.setDate(dt);

                String sDateApply = rs.getString(9);
                System.out.println(sDateApply);
                Date dtApply = null;
                try {
                    dtApply = sdf.parse(sDateApply);
                } catch (ParseException ex) {
                    System.out.println("problem in 2nd try catch");
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }
                datePickerApplication.setDate(dtApply);

                System.out.println(" before qual run ");

                String sDateQual = rs.getString(14);
                System.out.println(sDateQual);
                Date dtQual = null;
                try {
                    dtQual = sdf.parse(sDateQual);
                } catch (ParseException ex) {
                    System.out.println("problem in 2nd try catch");
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }
                datePickerQual.setDate(dtQual);
                System.out.println("How hjjjk");

                String gen = rs.getString(4);
                System.out.println("hiiii");
                if (gen.equals("Male")) {
                    System.out.println("hello");
                    txtComBoxGender.setSelectedIndex(0);
                } else if (gen.equals("Female")) {
                    txtComBoxGender.setSelectedIndex(1);
                } else {
                    txtComBoxGender.setSelectedIndex(2);
                }

                String sQualId = rs.getString(10);
                if (sQualId.equals("Matriculation")) {
                    txtComboBoxQual.setSelectedIndex(0);
                } else if (sQualId.equals("Hs")) {
                    txtComboBoxQual.setSelectedIndex(1);
                } else if (sQualId.equals("Graduation")) {
                    txtComboBoxQual.setSelectedIndex(2);
                } else {
                    txtComboBoxQual.setSelectedIndex(3);
                }

                String sZone = rs.getString(12);
                if (sZone.equals("Howrah")) {
                    txtComboBoxZone.setSelectedIndex(0);
                } else if (sZone.equals("Kolkata")) {
                    txtComboBoxZone.setSelectedIndex(1);
                } else if (sZone.equals("Hoogly")) {
                    txtComboBoxZone.setSelectedIndex(2);
                } else {
                    txtComboBoxZone.setSelectedItem(3);
                }

                txtEmail.setText(rs.getString(5));
                txtPhNo.setText(rs.getString(6));
                txtFatherName.setText(rs.getString(7));
                txtAddress.setText(rs.getString(8));
                txtMark.setText(rs.getString(11));

                String sPath = rs.getString(13);
                imagePath = sPath;
                System.out.println(sPath);
                lblPicImg.setIcon(imageAdjust(sPath, null));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

//        txtEmail.setText(model.getValueAt(rowIndex, 4).toString());
//        txtPhNo.setText(model.getValueAt(rowIndex, 5).toString());
//        txtID.setText(model.getValueAt(rowIndex, 0).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        if (isEmptyStudent()) {
            int id = Integer.parseInt(txtID.getText());
            if (student.isIdExists(id)) {
                if (!check()) {

                    String name = txtName.getText();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateDob = sdf.format(datePickerDob.getDate());
                    String dateApply = sdf.format(datePickerApplication.getDate());
                    String dateQual = sdf.format(datePickerQual.getDate());
                    String gender = txtComBoxGender.getSelectedItem().toString();
                    String qualId = txtComboBoxQual.getSelectedItem().toString();
                    String zone = txtComboBoxZone.getSelectedItem().toString();
                    String email = txtEmail.getText();
                    String phNo = txtPhNo.getText();
                    String address = txtAddress.getText();
                    String fatherName = txtFatherName.getText();
                    String marks = txtMark.getText();
                    student.update(id, name, dateDob, gender, email, phNo, fatherName, address,
                            marks, dateApply, dateQual, qualId, zone, imagePath);

                    jTable1.setModel(new DefaultTableModel(null, new Object[]{"Student Id", "Name", "DOB",
                        "Gender", "Email", "PhoneNo", "Application Date", "Image Path", "Qualification"}));
                    student.getStudentValue(jTable1, "");//   
                    clearStudent();
                }

            } else {
                JOptionPane.showMessageDialog(this, "student id doesn't exists");
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int id = Integer.parseInt(txtID.getText());
        if (student.isIdExists(id)) {
            student.delete(id);
            jTable1.setModel(new DefaultTableModel(null, new Object[]{"Student Id", "Name", "DOB",
                "Gender", "Email", "PhoneNo", "Application Date", "Image Path", "Qualification"}));
            student.getStudentValue(jTable1, "");//   
            clearStudent();
        } else {
            JOptionPane.showMessageDialog(this, "Student id is missing ");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        if (txtSearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "please enter a student id");
        } else {
            jTable1.setModel(new DefaultTableModel(null, new Object[]{"Student Id", "Name", "DOB",
                "Gender", "Email", "PhoneNo", "Application Date", "Image Path", "Qualification"}));
            student.getStudentValue(jTable1, txtSearch.getText());
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        jTable1.setModel(new DefaultTableModel(null, new Object[]{"Student Id", "Name", "DOB",
            "Gender", "Email", "PhoneNo", "Application Date", "Image Path", "Qualification"}));
        student.getStudentValue(jTable1, "");
        txtSearch.setText(null);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnAdminClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminClearActionPerformed
        // TODO add your handling code here:
        txtAdminName.setText(null);
        txtAdminUserName.setText(null);
        txtAdminPassword.setText(null);
//        txtAdminScQS.setText(null);
        txtAdminScAns.setText(null);
    }//GEN-LAST:event_btnAdminClearActionPerformed

    private void btnAdminLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminLogoutActionPerformed
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(this, "Do you want to log out now ?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0)
            this.dispose();
    }//GEN-LAST:event_btnAdminLogoutActionPerformed

    private void btnAdminUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminUpdateActionPerformed

        String newAdminName = txtAdminName.getText();
        System.out.println("new admin name " + newAdminName);
        String newAdminUsername = txtAdminUserName.getText();
        String newAdminPass = txtAdminPassword.getText();
        String newAdminScAns = txtAdminScAns.getText();
        String OldAdminPass = txtAdminOldPass.getText();

        System.out.println(adminId + " :" + newAdminPass + " " + newAdminScAns);
        try {
            ps = con.prepareStatement("select * from admin where id = ? and password = ?");
            ps.setInt(1, adminId);
            ps.setString(2, OldAdminPass);

            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                JOptionPane.showMessageDialog(null, "You enter wrong old password");

            } else {

                ps = con.prepareStatement("update admin set username=?,password=?,name=?,securityans=? where id=?");
                ps.setString(1, newAdminUsername);
                ps.setString(2, newAdminPass);
                ps.setString(3, newAdminName);
                ps.setString(4, newAdminScAns);

                adminUsername = newAdminUsername;
                adminPass = newAdminPass;

                JOptionPane.showMessageDialog(null, "Admin details updated successfully");
                fillAdminData();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnAdminUpdateActionPerformed

    private void txtAdminIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdminIdKeyTyped
        // TODO add your handling code here:
        txtAdminId.setEditable(false);
    }//GEN-LAST:event_txtAdminIdKeyTyped

    private void txtAdminScQSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdminScQSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdminScQSActionPerformed

    private void txtAdminScQSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdminScQSKeyTyped
        // TODO add your handling code here:
        evt.consume();
    }//GEN-LAST:event_txtAdminScQSKeyTyped

    private void btnForgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForgetActionPerformed

        ForgetPassword fg = new ForgetPassword();
        fg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnForgetActionPerformed

    private void btnExaminerLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminerLogOutActionPerformed
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(this, "Do you want to log out now ?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0){
            MainPage mp = new MainPage();
            mp.setVisible(true);
            this.dispose();
        }
        
    }//GEN-LAST:event_btnExaminerLogOutActionPerformed

    private void btnExaminerClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminerClearActionPerformed
        // TODO add your handling code here:
        clearExaminer();

    }//GEN-LAST:event_btnExaminerClearActionPerformed

    private void btnExaminerUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminerUpdateActionPerformed
        // TODO add your handling code here:
        if (isEmptyExaminer()) {
            int id = Integer.parseInt(txtExaminerId.getText());
            if (examiner.isIdExists(id)) {
                if (!checkExaminer()) {

                    String name = txtExaminerName.getText();
                    String phNo = txtExaminerPhNo.getText();
                    String userName = txtExaminerUsername.getText();
                    String password= txtExaminerPass.getText();
                    
                    examiner.update(id, name, phNo, userName, password);

                    jTable2.setModel(new DefaultTableModel(null, new Object[]{"Examiner Id", "Name", "PhoneNo","Username","Password"}));
                    examiner.getExaminerValue(jTable2, "");//   
                    clearExaminer();
                }

            } else {
                JOptionPane.showMessageDialog(this, "student id doesn't exists");
            }
        }
    }//GEN-LAST:event_btnExaminerUpdateActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        
        modelExaminer =(DefaultTableModel) jTable2.getModel();
        rowIndexExaminer = jTable2.getSelectedRow();
        txtExaminerId.setText(modelExaminer.getValueAt(rowIndexExaminer, 0).toString());
        txtExaminerName.setText(modelExaminer.getValueAt(rowIndexExaminer, 1).toString());
        txtExaminerPhNo.setText(modelExaminer.getValueAt(rowIndexExaminer, 2).toString());
        txtExaminerUsername.setText(modelExaminer.getValueAt(rowIndexExaminer, 3).toString());
        txtExaminerPass.setText(modelExaminer.getValueAt(rowIndexExaminer, 4).toString());
        
    }//GEN-LAST:event_jTable2MouseClicked

    private void btxSearchExaminerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btxSearchExaminerActionPerformed
        // TODO add your handling code here:
        if(txtSearchExaminer.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "please enter a Examiner id, name, phno , username");
        }else{
            jTable2.setModel(new DefaultTableModel(null, new Object[]{"Examiner Id", "Name", "PhoneNo","Username","Password"}));
            examiner.getExaminerValue(jTable2, txtSearchExaminer.getText());//   
             
        }
        
        
    }//GEN-LAST:event_btxSearchExaminerActionPerformed

    private void btnRefreshExaminerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshExaminerActionPerformed
        // TODO add your handling code here:
        jTable2.setModel(new DefaultTableModel(null, new Object[]{"Examiner Id", "Name", "PhoneNo","Username","Password"}));
        examiner.getExaminerValue(jTable2, "");
        txtSearchExaminer.setText(null);


        
    }//GEN-LAST:event_btnRefreshExaminerActionPerformed

    private void btnExaminerDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminerDeleteActionPerformed
        // TODO add your handling code here:
        
        
        int id = Integer.parseInt(txtExaminerId.getText());
        if(examiner.isIdExists(id))
        {
            examiner.delete(id);
            jTable2.setModel(new DefaultTableModel(null, new Object[]{"Examiner Id", "Name", "PhoneNo","Username","Password"}));

  
            examiner.getExaminerValue(jTable2, "");    
            clearExaminer();
        }else{
            JOptionPane.showMessageDialog(this, "Examiner id is missing ");
        }
    }//GEN-LAST:event_btnExaminerDeleteActionPerformed

    private ImageIcon imageAdjust(String path, byte[] pic) {
        ImageIcon myImage = null;
        if (path != null) {
            myImage = new ImageIcon(path);
        } else {
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image newImage = img.getScaledInstance(lblPicImg.getWidth(), lblPicImg.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(newImage);
        return icon;
    }

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdminClear;
    private javax.swing.JButton btnAdminLogout;
    private javax.swing.JButton btnAdminUpdate;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExaminerAdd;
    private javax.swing.JButton btnExaminerClear;
    private javax.swing.JButton btnExaminerDelete;
    private javax.swing.JButton btnExaminerLogOut;
    private javax.swing.JButton btnExaminerPrint;
    private javax.swing.JButton btnExaminerUpdate;
    private javax.swing.JButton btnForget;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRefreshExaminer;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnpic;
    private javax.swing.JButton btxSearchExaminer;
    private com.toedter.calendar.JDateChooser datePickerApplication;
    private com.toedter.calendar.JDateChooser datePickerDob;
    private com.toedter.calendar.JDateChooser datePickerQual;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblPicImg;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAdminId;
    private javax.swing.JTextField txtAdminName;
    private javax.swing.JPasswordField txtAdminOldPass;
    private javax.swing.JTextField txtAdminPassword;
    private javax.swing.JTextField txtAdminScAns;
    private javax.swing.JTextField txtAdminScQS;
    private javax.swing.JTextField txtAdminUserName;
    private javax.swing.JComboBox<String> txtComBoxGender;
    private javax.swing.JComboBox<String> txtComboBoxQual;
    private javax.swing.JComboBox<String> txtComboBoxZone;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtExaminerId;
    private javax.swing.JTextField txtExaminerName;
    private javax.swing.JTextField txtExaminerPass;
    private javax.swing.JTextField txtExaminerPhNo;
    private javax.swing.JTextField txtExaminerUsername;
    private javax.swing.JTextField txtFatherName;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMark;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhNo;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchExaminer;
    // End of variables declaration//GEN-END:variables
}

// date er jinis pati ache string teke date e convertion        
//        String dobDate = datePickerDob.toString();
//        String applicationDate = datePickerApplication.toString();
//        String qualDate = datePickerQual.toString();
//        System.out.println("dob "+dobDate+" application "+applicationDate+" qualDate "+qualDate);
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//        Date thisDate = new Date();
//        System.out.println(thisDate);
//        
//        
//        
//        Date dtdob = null,dtApply = null,dtQual = null;
//        try {
//            dtdob = sdf.parse(dobDate); //exception throw kore
//            dtApply = sdf.parse(applicationDate);          //exception throw kore 
//            dtQual = sdf.parse(qualDate);          //exception throw kore 
//        } catch (ParseException ex) {
//            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//                    String birthdate = datePickerDob.toString();
//            String applidate = datePickerApplication.toString();
//            String qualDate = datePickerQual.toString();
//database teke fetch kore frame e dekachhi
//        try {  
//            Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(rowIndex, 2).toString());
//            datePickerDob.setDate(dt);
//        } catch (ParseException ex) {
//            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        String gender = model.getValueAt(rowIndex, 3).toString();
//        if(gender.equals("Male")){
//            txtComBoxGender.setSelectedIndex(0);
//        }else if(gender.equals("Female")){
//            txtComBoxGender.setSelectedIndex(1);
//        }else{
//            txtComBoxGender.setSelectedIndex(2);
//        }
