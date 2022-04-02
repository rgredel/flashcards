package pl.gredel.flashcards.db.conf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInizializer {

    private String getStatement(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/flashcard.sql"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        String collect = reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
        return collect;
    }

    public void init() {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(getStatement());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }

    }
}
