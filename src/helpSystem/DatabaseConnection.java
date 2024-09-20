package helpSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:helpsys.db";

    public static Connection getConnection() throws SQLException {
        // Establish a connection to the database using the DriverManager
        return DriverManager.getConnection(URL);
    }
}
