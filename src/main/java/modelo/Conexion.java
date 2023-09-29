package modelo;

import java.sql.DriverManager;
import java.sql.Connection;
import java.util.Properties;

public class Conexion {
    private static final String dbHost = System.getenv("DB_HOST");
    private static final String dbPort = System.getenv("DB_PORT");
    private static final String database = System.getenv("DB_DATABASE");
    private static final String dbUsername = System.getenv("DB_USERNAME");
    private static final String dbPassword = System.getenv("DB_PASSWORD");

    private static final String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + database;
    private static Connection connection = null;

    public Conexion() {
        try {
            if (connection == null) {
                Properties properties = new Properties();
                properties.setProperty("user", dbUsername);
                properties.setProperty("password", dbPassword);
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, properties);
                connection.setAutoCommit(false); //para manejar manualmente las transacciones
                System.out.println("conexion exitosa con la DB");
            }
        } catch (Exception exception) {
            System.err.println("error al conectarse con la DB");
            System.err.println(exception.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
