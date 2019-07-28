package studigochi.test.listeners;

import org.jetbrains.annotations.Contract;
import org.sqlite.JDBC;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * {@inheritDoc}<br/>
 *     Handles initializing and closing the Database connection on servletContext start/destruction
 */
@WebListener("Listener to initialize a sqlite database")
public class DBServletContextListener implements ServletContextListener {

    /**
     * We are using a file-based Database since the specifications given at the beginning forbade using local servers.<br/>
     * Hence the DB file lies in the webinf folder
     */
    private static final String databaseName = "WEB-INF/studigochi.db";

    /**
     * Holds the DB connection instance
     */
    private static Connection connection;

    /**
     * @return An instance of the DB connection
     */
    @Contract(pure = true)
    public static Connection getConnection() {
        //TODO: Handle auto-closing of the connection after timeout
        // -> If the connection is not used for too long then the Connection may auto-close
        return connection;
    }

    /**
     * {@inheritDoc}<br/>
     *     Initializes the Database Connection
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //Build the direct string on the file system
        final String connectionString = JDBC.PREFIX + sce.getServletContext().getRealPath(databaseName);

        try {
            connection = JDBC.createConnection(connectionString, new Properties());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}<br/>
     *     Destroys the Database Connection
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        //If connection not destroyed yet, destroy it
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
