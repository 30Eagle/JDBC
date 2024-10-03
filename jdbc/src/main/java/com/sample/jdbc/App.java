package com.sample.jdbc;

import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/jfsd"; // Database URL
        String username = "root"; // MySQL username
        String password = "root"; // MySQL password (replace with actual password)
        
        // SQL query to select all data from the "jobs" table
        String query = "SELECT * FROM jobs"; 

        // Load and register the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish a connection to the database
        Connection con = DriverManager.getConnection(url, username, password);

        // Create a Statement object to execute SQL queries
        Statement st = con.createStatement();

        // Execute the SQL query and retrieve the results in a ResultSet object
        ResultSet rs = st.executeQuery(query);

        // Get the metadata to determine the number of columns in the result set
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        // Loop through the ResultSet to fetch each row of data
        while (rs.next()) {
            // Print each column in the row
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getString(i) + "\t"); // Print each column separated by a tab
            }
            System.out.println(); // Move to the next line after each row
        }

        // Close the Statement object
        st.close();
        
        // Close the Connection object
        con.close();
    }
}
