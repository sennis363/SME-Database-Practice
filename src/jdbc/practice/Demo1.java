package jdbc.practice;
/*
 * @(#)SimpleDB.java	2.00 May 19, 2004
 *
 * Copyright (c) 2003-2004 Waukesha County Technical College. All Rights Reserved.
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF
 * THE ACADEMIC FREE LICENSE V2.0 ("AGREEMENT"). ANY USE, REPRODUCTION
 * OR DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT'S ACCEPTANCE
 * OF THIS AGREEMENT. A COPY OF THE AGREEMENT MUST BE ATTACHED TO ANY
 * AND ALL ORIGINALS OR DERIVITIVES.
 */

// Mandatory for JDBC
import java.sql.*;

/**
 * Here's a simple example of how to connect to and use a database from Java.
 * Note that this example sacrifices good OOP design for simplicity. Do you
 * think you can identify ways to improve on this design?
 *
 * Instructions: You need four or five things to work with Java Database
 * Connectivity (JDBC): 1. A suitable database driver (prefere Type 4). We'll
 * use the Microsoft SQL Server driver 2. The driver needs to be on the
 * classpath, which means it must be in your libraries directory. 3. Acess to a
 * database. We'll use WCTC's sample Employee database, accessible from
 * anywhere. 4. A database URL (Uniform Resource Locator) string, which is
 * essentially the address of your database or database server. 5. The fully
 * qualified name of the driver class 6. a user name and password providing
 * access to the database.
 *
 * @author Jim Lombardo
 */
public class Demo1 {

    private Connection conn;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;

    public static void main(String[] args) {
        Demo1 db = new Demo1();
        db.driverClassName = "com.mysql.jdbc.Driver";
        db.url = "jdbc:mysql://localhost:3306/hr";
        db.userName = "root";
        db.password = "admin";
        try {
            Class.forName(db.driverClassName);
            db.conn = DriverManager.getConnection(db.url, db.userName, db.password);
        } catch (ClassNotFoundException cnfex) {
            System.err.println(
                    "Error: Failed to load JDBC driver!");
            cnfex.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlex) {
            System.err.println("Error: Unable to connect to database!");
            sqlex.printStackTrace();
            System.exit(1);
        }
        Statement stmt = null;
        ResultSet rs = null;
//		String sql1 = "SELECT * FROM [dbo].[EMPLOYEE] WHERE "
//                        + "HIREDATE > '1/1/1988' ORDER BY LASTNAME";
        String sql2 = "SELECT * FROM hr.employee";
//                String sql3 = "SELECT * FROM employee WHERE active < 1 ORDER BY last_name";
        String sql4 = "DELETE * FROM hr.employee WHERE active < 1";

        try {
            stmt = db.conn.createStatement();
//			rs = stmt.executeQuery(sql2);
//            int recordsDeleted = stmt.executeUpdate("DELETE FROM employee WHERE employee_id=3");
//            int recordsAdded = stmt.executeUpdate("INSERT INTO hr.employee VALUES ('3','Nilsen', 'Johan', 'Bakken 2', 'Stavanger')");


            System.out.println("============================");
            System.out.println("Output from MySQL Server...");
            System.out.println("============================");
            int count = 0;
            while (rs.next()) {
                System.out.println("\nRecord No: " + (count + 1));
                System.out.println("\nID: " + rs.getInt("employee_id"));
                System.out.println("Last Name: " + rs.getString("last_name"));
                System.out.println("First Name: " + rs.getString("first_name"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Active Status: " + rs.getInt("active"));
                count++;
            }
            System.out.println("==================\n" + count + " records found.");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                stmt.close();
                db.conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
