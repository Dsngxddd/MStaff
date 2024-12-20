package net.cengiz1.mstaff.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static Connection connection;
    public static void connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            String host = ConfigManager.getMySQLHost();
            int port = ConfigManager.getMySQLPort();
            String database = ConfigManager.getMySQLDatabase();
            String user = ConfigManager.getMySQLUsername();
            String password = ConfigManager.getMySQLPassword();

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("MySQL bağlantısı başarılı.");
        } catch (SQLException e) {
            System.err.println("MySQL bağlantısı başarısız!");
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }
    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("MySQL bağlantısı kapatıldı.");
            }
        } catch (SQLException e) {
            System.err.println("MySQL bağlantısı kapatılamadı!");
            e.printStackTrace();
        }
    }
}
