package crowdfund.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    // Database URL. Specifies the database to connect to (crowdfund_db) on localhost using port 3306.
    private static final String URL = "jdbc:mysql://localhost:3306/crowdfund_db";
    // Database username.  The user account used to connect to the database.
    private static final String USER = "root";
    // Database password.  In this case, the password is empty (often not recommended for production).
    private static final String PASS = "";

    // Method to establish a database connection.
    public static Connection getConnection() {
        try {
            // Attempts to establish a connection to the database using the provided URL, USER, and PASS.
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            // If a connection fails, print the stack trace for debugging purposes.
            e.printStackTrace();
            // Returns null if the connection could not be established.
            return null;
        }
    }
}
