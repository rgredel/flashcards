package pl.gredel.flashcards.service.util;

import pl.gredel.flashcards.db.dao.util.DAOException;

import java.sql.SQLException;

public class ServiceException extends Exception{
    public ServiceException(String massage) {
        super(massage);
    }

    public ServiceException(String massage, DAOException daoException) {
        super(massage, daoException);
    }
}
