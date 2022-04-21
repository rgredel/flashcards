package pl.gredel.flashcards.db.conf;


import org.apache.commons.dbcp2.BasicDataSource;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger( ConnectionPool.class.getName());
    private static BasicDataSource ds = new BasicDataSource();
    private static Properties dbProperties;

        static {
            dbProperties = new Properties();
            try {
                dbProperties.load(ConnectionPool.class.getResourceAsStream("/db.properties"));
            }catch (IOException e){
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }

            String url = dbProperties.getProperty("url");
            String username = dbProperties.getProperty("username");
            String password = dbProperties.getProperty("password");

            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
        }

        private ConnectionPool(){}
        //TODO
        //refactor
        public static Connection getConnection() throws SQLException {
            Connection connection = null;
            try {
                Context initContext = new InitialContext();
                Context envContext = null;
                envContext = (Context) initContext.lookup("java:/comp/env");
                DataSource dataSource = (DataSource) envContext.lookup("jdbc/flashcard");
                connection = dataSource.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return connection;

               // return ds.getConnection();
        }


}
