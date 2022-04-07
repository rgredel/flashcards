package pl.gredel.flashcards.db.dao.util;

import java.util.List;
import java.util.Optional;


public abstract class DataAccessObject <T extends DataTransferObject> {

    public abstract Optional<T> findById(int id) throws DAOException;
    public abstract List<T> findAll() throws DAOException;
    public abstract T update(T dto) throws DAOException;
    public abstract T create(T dto) throws DAOException;
    public abstract void delete(int id) throws DAOException;

}
