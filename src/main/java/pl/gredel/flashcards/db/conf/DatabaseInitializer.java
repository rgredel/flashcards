package pl.gredel.flashcards.db.conf;

import pl.gredel.flashcards.db.dao.util.DataAccessObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    private static final Logger LOGGER = Logger.getLogger( DatabaseInitializer.class.getName() );


    private String getStatement(){
        BufferedReader reader = null;
        String collect;
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/flashcard.sql"));

            collect = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new RuntimeException(e);
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }

        return collect;
    }

    public void init() {
        Connection connection;
        try {
            connection = ConnectionPool.getConnection();
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new RuntimeException(sqlException);
        }

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(getStatement());
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new RuntimeException(sqlException);
        }

    }
}
