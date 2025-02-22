# Examination-Management-System

 Admin Panel:
Administrators have access to a centralized dashboard where they can manage 
user accounts, including students and examiners. They can add, delete, or 
update user details, verify student eligibility, and generate login credentials.
Administrators possess the authority to manage examiner profiles, thereby 
facilitating the addition, deletion, and modification of their details too.
 Examiner Panel:
Examiners can create, edit, and delete question papers through a dedicated 
interface. They can also set parameters such as time limits, question types, and 
difficulty levels to customize assessments according to specific requirements.
 Student Portal:
Only those Students who are eligible by administrator can log in by using their 
email and phone no and see their details in dashboard and can download their 
admit too. They can view their examination schedules, download admit cards, 
and submit responses during examinations. After completion, they can access 
their results and performance reports securely

Setting up a Database :
(a) Establishing a Connection
 Class.forName("com.mysql.cj.jdbc.Driver");
Your driver documentation will give you the class name to use. For instance, if 
the class name is jdbc.DriverXYZ , you would load the driver with the following 
line of code: 
Class.forName("jdbc.DriverXYZ"); 
You do not need to create an instance of a driver and register it with the 
DriverManager because calling Class.forName will do that for you 
automatically. If you were to create your own instance, you would be creating 
an unnecessary duplicate, but it would do no harm. 
When you have loaded a driver, it is available for making a connection with a 
DBMS. 
(b)Making the Connection 
The second step in establishing a connection is to have the appropriate driver 
connect to the DBMS. The following line of code illustrates the general idea: 
Connection con = DriverManager.getConnection(url, "myLogin", 
"myPassword");

you put your password for the DBMS. So if you log in to your DBMS with a login 
name of " Fernanda " and a password of " J8, " just these two lines of code will 
establish a connection:
String url = 
"jdbc:mysql://localhost:3306/studentdb?zeroDateTimeBehavior=CONVERT_
TO_NULL";
Connection con = DriverManager.getConnection(url, "root", "
"MySql@00"");
(c)Creating JDBC Statements
A Statement object is what sends your SQL statement to the DBMS. You simply 
create a Statement object and then execute it, supplying the appropriate 
execute method with the SQL statement you want to send. For a SELECT 
statement, the method to use is executeQuery . For statements that create or 
modify tables, the method to use is executeUpdate . It takes an instance of an 
active connection to create a Statement object. In the following example, we 
use our Connection object con to create the Statement object stmt :
Statement stmt = con.createStatement();
At this point stmt exists, but it does not have an SQL statement to pass on to 
the DBMS. We need to supply that to the method we use to execute stmt. For 
example, in the following code fragment, we supply executeUpdate with the 
SQL statement from the example above:
stmt.executeUpdate("CREATE TABLE STUDENTDATA" + "(STUDENTNAME 
VARCHAR(32), ID INTEGER, EMAIL STRING, " + "SALES INTEGER, MARKS
INTEGER)");
Since we made a string out of the SQL statement and assigned it to the variable 
createTableStudent , we could have written the code in this alternate form:
stmt.executeUpdate(createTableStudent);


