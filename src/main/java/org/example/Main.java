package org.example;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        // 1. import package
        // packages downloaded from the mnv repository

        // 2. load
        // 3. register
        // internally from java 6 it will take care of it

        // 4. create connection
        String url="Replace with the connection URL";
        String uname="Replace with the username";
        String password="Replace with correct password";
        Connection con = DriverManager.getConnection(url, uname, password);

        System.out.println("Connection established with MySQL to the schema DB");

        // 5. create statement
        Statement st = con.createStatement();

        // 6. execute statement
         create(con); //create operation using prepared statement
         read(st); // read operation using statement
         update(st); // update operation
         delete(con); // delete operation using called procedures

        // 7. close
        con.close();
    }

    public static void read(Statement st) throws SQLException {
        String sql = "select * from Student";
        ResultSet results = st.executeQuery(sql);

        while(results.next()){
            System.out.println(results.getString("sname") + " : " + results.getString("marks"));
        }
    }

    public static void create(Connection con) throws SQLException {
        int sid = 5;
        String sname = "Ram";
        int marks = 80;

        String sql = "insert into student (sid, sname, marks) values (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, sid);
        ps.setString(2, sname);
        ps.setInt(3, marks);
        ps.executeUpdate();
        System.out.println("Row added");
    }

    public static void update(Statement st) throws SQLException {
        String sql = "update student set sname='Max' where sname ='Ram'";
        st.execute(sql);
        System.out.println("Row modified");
    }

    public static void delete(Connection con) throws SQLException {
        // Add this stored procedure in the MySQL database
        /*
         * CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteStudent`(IN studentID INT)
         * BEGIN
         * 	DELETE FROM STUDENT WHERE SID = studentID;
         * END
         */
        int sid = 5;
        String storedProcedure = "{call DeleteStudent(?)}";
        CallableStatement cs = con.prepareCall(storedProcedure);
        cs.setInt(1,sid);
        cs.execute();
        System.out.println("Row deleted");
    }
}