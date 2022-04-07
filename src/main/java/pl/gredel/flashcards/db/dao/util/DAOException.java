package pl.gredel.flashcards.db.dao.util;

import java.sql.SQLException;

public class DAOException extends Exception{
    public DAOException(String massage) {
        super(massage);
    }

    public DAOException(String massage, SQLException sqlException) {
        super(massage, sqlException);
    }
}
