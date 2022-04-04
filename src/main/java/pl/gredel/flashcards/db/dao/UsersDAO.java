package pl.gredel.flashcards.db.dao;

import pl.gredel.flashcards.db.dao.util.DataAccessObject;
import pl.gredel.flashcards.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO extends DataAccessObject<Users> {

    private static final String INSERT = "INSERT INTO Users(login, password, email) VALUES (?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT id, login, password, email FROM Users WHERE id=?";
    private static final String FIND_ALL = "SELECT id, login, password, email FROM Users";
    private static final String LAST_ID = "SELECT max(ID) FROM Users";
    private static final String UPDATE = "UPDATE Users SET login=?, password=?, email=?  WHERE id=?";
    private static final String DELETE = "DELETE FROM Users WHERE id=?";

    @Override
    public Users findById(int id) {
        Users user = new Users();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return user;
    }

    @Override
    public List<Users> findAll() {
        List<Users> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
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
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return users;
    }

    @Override
    public Users update(Users dto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,dto.getLogin());
            preparedStatement.setString(2,dto.getPassword());
            preparedStatement.setString(3,dto.getEmail());
            preparedStatement.setInt(4,dto.getId());
            preparedStatement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return findById(dto.getId());
    }

    @Override
    public Users create(Users dto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, dto.getLogin());
            preparedStatement.setString(2, dto.getPassword());
            preparedStatement.setString(3, dto.getEmail());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(LAST_ID);

            ResultSet resultSet = preparedStatement.executeQuery();

            int id;

            while (resultSet.next()) {
                id = resultSet.getInt(1);
                dto = (findById(id));
            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
    }
}
