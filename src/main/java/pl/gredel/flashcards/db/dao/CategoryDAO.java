package pl.gredel.flashcards.db.dao;

import pl.gredel.flashcards.db.dao.util.DataAccessObject;
import pl.gredel.flashcards.model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDAO extends DataAccessObject<Category> {

    private static final Logger LOGGER = Logger.getLogger( CategoryDAO.class.getName() );


    private static final String INSERT = "INSERT INTO Category(name) VALUES (?)";
    private static final String FIND_BY_ID = "SELECT id, name FROM Category WHERE id=?";
    private static final String FIND_ALL = "SELECT id, name FROM Category";
    private static final String LAST_ID = "SELECT max(ID) FROM Category";
    private static final String UPDATE = "UPDATE Category SET name = ? WHERE id=?";
    private static final String DELETE = "DELETE FROM Category WHERE id=?";



    @Override
    public Category findById(int id) {
        Category category = new Category();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new RuntimeException(sqlException);
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
                categories.add(category);
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new RuntimeException(sqlException);
        }
        return categories;
    }

    @Override
    public Category update(Category dto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,dto.getName());
            preparedStatement.setInt(2,dto.getId());
            preparedStatement.execute();

        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new RuntimeException(sqlException);
        }
        return findById(dto.getId());
    }

    @Override
    public Category create(Category dto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,dto.getName());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(LAST_ID);

            ResultSet resultSet = preparedStatement.executeQuery();
            int id;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                dto = (findById(id));
            }


        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new RuntimeException(sqlException);
        }
        return dto;
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
            throw new RuntimeException(sqlException);
        }
    }
}
