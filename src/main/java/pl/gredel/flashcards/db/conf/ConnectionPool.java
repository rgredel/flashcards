package pl.gredel.flashcards.db;


import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
        private static BasicDataSource ds = new BasicDataSource();

        //TODO
        //Read properties from file

        static {
            ds.setUrl("jdbc:postgresql://localhost/flashcard");
            ds.setUsername("postgres");
            ds.setPassword("root");
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
        }


        public static Connection getConnection() throws SQLException {
                return ds.getConnection();
        }

        private ConnectionPool(){ }
}
