package pl.gredel.flashcards.db.dao;

import pl.gredel.flashcards.db.conf.ConnectionPool;
import pl.gredel.flashcards.db.dao.util.DataAccessObject;
import pl.gredel.flashcards.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersDAO extends DataAccessObject<Users> {

    private static final Logger LOGGER = Logger.getLogger( UsersDAO.class.getName() );

    private static final String INSERT = "INSERT INTO Users(login, password, email) VALUES (?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT id, login, password, email FROM Users WHERE id=?";
    private static final String FIND_BY_LOGIN = "SELECT id, login, password, email FROM Users WHERE login=?";
    private static final String FIND_ALL = "SELECT id, login, password, email FROM Users";
    private static final String UPDATE = "UPDATE Users SET login=?, password=?, email=?  WHERE id=?";
    private static final String DELETE = "DELETE FROM Users WHERE id=?";

    @Override
    public Optional<Users> findById(int id) {
        Users user = null;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = new Users();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return Optional.ofNullable(user);
    }


    public Optional<Users> findByLogin(String login) {
        Users user = null;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN) ){
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = new Users();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<Users> findAll() {
        List<Users> users = new ArrayList<>();

        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL) ){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Users user = new Users();

                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));

                users.add(user);
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return users;
    }

    @Override
    public Users update(Users dto) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE) ){
            preparedStatement.setString(1,dto.getLogin());
            preparedStatement.setString(2,dto.getPassword());
            preparedStatement.setString(3,dto.getEmail());
            preparedStatement.setInt(4,dto.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0 ) throw new SQLException("Update Users failed.");

        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return findById(dto.getId()).get();
    }

    @Override
    public Users create(Users dto) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS) ){
            preparedStatement.setString(1, dto.getLogin());
            preparedStatement.setString(2, dto.getPassword());
            preparedStatement.setString(3, dto.getEmail());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0 ) throw new SQLException("Creating Users failed.");

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dto.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating Users failed, no ID obtained.");
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return dto;
    }

    @Override
    public void delete(int id) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE) ){
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0 ) throw new SQLException("Deleting failed.");
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
    }
}
