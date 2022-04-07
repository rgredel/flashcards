package pl.gredel.flashcards.db.dao.util;

import pl.gredel.flashcards.db.conf.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DataAccessObject <T extends DataTransferObject> {

    public abstract Optional<T> findById(int id);
    public abstract List<T> findAll();
    public abstract T update(T dto);
    public abstract T create(T dto);
    public abstract void delete(int id);

}
