package pl.gredel.flashcards.db.dao.util;

import pl.gredel.flashcards.db.conf.ConnectionPool;
import pl.gredel.flashcards.db.dao.CategoryDAO;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DataAccessObject <T extends DataTransferObject> {
    protected final Connection connection;
    private static final Logger LOGGER = Logger.getLogger( DataAccessObject.class.getName() );

    public DataAccessObject(){
        super();
        try {
            this.connection = ConnectionPool.getConnection();
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new RuntimeException(sqlException);
        }
    }

    public abstract T findById(int id);
    public abstract List<T> findAll();
    public abstract T update(T dto);
    public abstract T create(T dto);
    public abstract void delete(int id);

}
