package studigochi.test.listeners;

import org.jetbrains.annotations.Contract;
import org.sqlite.JDBC;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@WebListener("Listener to initialize a sqlite database")
public class DBServletContextListener implements ServletContextListener {

    private static final String databaseName = "WEB-INF/studigochi.db";
    private static Connection connection;

    @Contract(pure = true)
    public static Connection getConnection() {
        return connection;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //final String connectionString = JDBC.PREFIX + sce.getServletContext().getRealPath(databaseName);
        final String connectionString = JDBC.PREFIX + "C:\\Users\\Timo Klenk\\Documents\\Projects\\SourceTree\\Studium\\TomCat\\Studigochi_First\\web\\WEB-INF\\studigochi.db";

        System.out.printf("%n%n%n%nContext Initialized%n%n%n%n");

        try {
            connection = JDBC.createConnection(connectionString, new Properties());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.printf("%n%n%n%nContext Destroyed%n%n%n%n");

        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
