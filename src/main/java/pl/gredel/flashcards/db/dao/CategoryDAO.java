package pl.gredel.flashcards.db.dao;

import pl.gredel.flashcards.db.conf.ConnectionPool;
import pl.gredel.flashcards.db.dao.util.DAOException;
import pl.gredel.flashcards.db.dao.util.DataAccessObject;
import pl.gredel.flashcards.model.Category;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDAO extends DataAccessObject<Category> {

    private static final Logger LOGGER = Logger.getLogger( CategoryDAO.class.getName() );

    private static final String INSERT = "INSERT INTO Category(name) VALUES (?)";
    private static final String FIND_BY_ID = "SELECT id, name FROM Category WHERE id=?";
    private static final String FIND_ALL = "SELECT id, name FROM Category";
    private static final String UPDATE = "UPDATE Category SET name = ? WHERE id=?";
    private static final String DELETE = "DELETE FROM Category WHERE id=?";



    @Override
    public Optional<Category> findById(int id) throws DAOException {
        Category category = null;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                category = new Category();
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new DAOException("Find Category failed.", sqlException);
        }

        return Optional.ofNullable(category);
    }

    @Override
    public List<Category> findAll() throws DAOException {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
                categories.add(category);
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new DAOException("Find all Categories failed.", sqlException);
        }
        return categories;
    }

    @Override
    public Category update(Category dto) throws DAOException {
        try (Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1,dto.getName());
            preparedStatement.setInt(2,dto.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0 ) throw new DAOException("Category wasn't updated.");

            return findById(dto.getId()).get();
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new DAOException("Updating Category failed.", sqlException);
        }
    }

    @Override
    public Category create(Category dto) throws DAOException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1,dto.getName());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0 ) throw new DAOException("Category wasn't created");

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dto.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new DAOException("Creating Category failed, no ID obtained.");
                }
            }

        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new DAOException("Creating Category failed.", sqlException);
        }
        return dto;
    }

    @Override
    public void delete(int id) throws DAOException {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new DAOException("Deleting Category failed.", sqlException);
        }
    }
}
