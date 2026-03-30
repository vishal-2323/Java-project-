import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb",
                "root",
                "password" // change your MySQL password
            );
        } catch (Exception e) {
            System.out.println("Database connection error");
            return null;
        }
    }
}
