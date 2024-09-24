package main.com.baticuisine.DatabaseConnection;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;




    private static final String URL = "jdbc:postgresql://localhost:5432/";


    private DatabaseConnection() throws SQLException {
        try {



            // Load environment variables from .env file
             final Dotenv dotenv = Dotenv.configure().load();
            // Retrieve variables
            String dbName = dotenv.get("DB_NAME");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");

            // Build the database URL with the environment variable
            String databaseUrl = URL+dbName;
            this.connection = DriverManager.getConnection(URL, dbUser, dbPassword);
            System.out.println("Connection resussie!");
            System.out.println(connection);
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            throw new SQLException("Failed to connect to the database.", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnection getInstance() throws SQLException {

        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}



