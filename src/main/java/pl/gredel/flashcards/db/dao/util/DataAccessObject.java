package pl.gredel.flashcards.db.dao.util;

import pl.gredel.flashcards.db.conf.ConnectionPool;

import java.sql.*;
import java.util.List;

public abstract class DataAccessObject <T extends DataTransferObject> {
    protected final Connection connection;

    public DataAccessObject(){
        super();
        try {
            this.connection = ConnectionPool.getConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
    }

    public abstract T findById(int id);
    public abstract List<T> findAll();
    public abstract T update(T dto);
    public abstract T create(T dto);
    public abstract void delete(int id);

}
