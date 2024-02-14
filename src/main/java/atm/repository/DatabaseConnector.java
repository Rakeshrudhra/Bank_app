package atm.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/Bank_userDetails";
    private static final String USERNAME = "rakesh";
    private static final String PASSWORD = "R@vi1234";

    private static DatabaseConnector instance;
    private Connection connection;

    private DatabaseConnector() {
        this.connection = createConnection();
    }

    public static synchronized DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    private Connection createConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("\u001B[34m" + "Connected to the database.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error connecting to the database.");
        }

        return connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
