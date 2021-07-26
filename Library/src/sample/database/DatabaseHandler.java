package sample.database;

import java.sql.*;

public class DatabaseHandler {
    private static DatabaseHandler handler;

    private static Connection connection = null;
    private static Statement statement = null;


    public DatabaseHandler() {
        createConnection();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Bastian21");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resultSet;
    }

    public boolean execAction(String query) {
        try {
            statement = connection.createStatement();
            statement.execute(query);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
