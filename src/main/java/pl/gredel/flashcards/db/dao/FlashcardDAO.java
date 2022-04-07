package pl.gredel.flashcards.db.dao;

import pl.gredel.flashcards.db.conf.ConnectionPool;
import pl.gredel.flashcards.db.dao.util.DataAccessObject;
import pl.gredel.flashcards.model.Flashcard;
import pl.gredel.flashcards.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlashcardDAO extends DataAccessObject<Flashcard> {

    private static final Logger LOGGER = Logger.getLogger( FlashcardDAO.class.getName() );


    private static final String INSERT = "INSERT INTO Flashcard(title, question, answer, level, is_public, user_id, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM Flashcard WHERE id=?";
    private static final String FIND_ALL = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM flashcard";
    private static final String UPDATE = "UPDATE Flashcard SET title = ?, question = ?, answer = ?, level = ?, is_public = ?, user_id = ?, category_id = ? WHERE id=?";
    private static final String DELETE = "DELETE FROM Flashcard WHERE id=?";
    private static final String FIND_ALL_PUBLIC = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM flashcard WHERE is_public='t'";
    private static final String FIND_ALL_BY_USER = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM flashcard WHERE user_id=?";
    private static final String FIND_ALL_BY_DECK = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM flashcard join deck_flashcard on id = flashcard_id WHERE deck_flashcard.deck_id=?";


    @Override
    public Optional<Flashcard> findById(int id) {
        Flashcard flashcard = null;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID) ){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                flashcard = new Flashcard();
                flashcard.setId(resultSet.getInt(1));
                flashcard.setTitle(resultSet.getString(2));
                flashcard.setQuestion(resultSet.getString(3));
                flashcard.setAnswer(resultSet.getString(4));
                flashcard.setLevel(resultSet.getInt(5));
                flashcard.setPublic(resultSet.getBoolean(6));
                UsersDAO usersDAO = new UsersDAO();
                Users user = usersDAO.findById(resultSet.getInt(7)).get();
                CategoryDAO categoryDAO = new CategoryDAO();
                flashcard.setCategory(categoryDAO.findById(resultSet.getInt(8)).get());
                flashcard.setUser(user);
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return Optional.ofNullable(flashcard);
    }


    @Override
    public List<Flashcard> findAll() {
        List<Flashcard> flashcards = new ArrayList<>();

        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){
            flashcards.addAll(findAllTemplate(preparedStatement));
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return flashcards;
    }

    public List<Flashcard> findAllPublic() {
        List<Flashcard> flashcards = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PUBLIC) ){
            flashcards.addAll(findAllTemplate(preparedStatement));
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return flashcards;
    }
    public List<Flashcard> findAllByDeckId(int idDeck) {
        List<Flashcard> flashcards = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DECK) ){
            preparedStatement.setInt(1, idDeck);
            flashcards.addAll(findAllTemplate(preparedStatement));
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return flashcards;
    }

    public List<Flashcard> findAllByUserId(int id) {
        List<Flashcard> flashcards = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USER)){
            preparedStatement.setInt(1, id);
            flashcards.addAll(findAllTemplate(preparedStatement));
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return flashcards;
    }

    private List<Flashcard> findAllTemplate(PreparedStatement preparedStatement) throws SQLException {
        List<Flashcard> flashcards = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Flashcard flashcard = new Flashcard();
                flashcard.setId(resultSet.getInt(1));
                flashcard.setTitle(resultSet.getString(2));
                flashcard.setQuestion(resultSet.getString(3));
                flashcard.setAnswer(resultSet.getString(4));
                flashcard.setLevel(resultSet.getInt(5));
                flashcard.setPublic(resultSet.getBoolean(6));
                UsersDAO usersDAO = new UsersDAO();
                Users user = usersDAO.findById(resultSet.getInt(7)).get();
                CategoryDAO categoryDAO = new CategoryDAO();
                flashcard.setCategory(categoryDAO.findById(resultSet.getInt(8)).get());
                flashcard.setUser(user);
                flashcards.add(flashcard);
            }
        return flashcards;
    }

    @Override
    public Flashcard update(Flashcard dto) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE) ){
            preparedStatement.setString(1,dto.getTitle());
            preparedStatement.setString(2,dto.getQuestion());
            preparedStatement.setString(3,dto.getAnswer());
            preparedStatement.setInt(4,dto.getLevel());
            preparedStatement.setBoolean(5,dto.isPublic());
            preparedStatement.setInt(6,dto.getUser().getId());
            preparedStatement.setInt(7,dto.getCategory().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0 ) throw new SQLException("Update Flashcard failed.");
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return findById(dto.getId()).get();
    }

    @Override
    public Flashcard create(Flashcard dto) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS) ){

            preparedStatement.setString(1,dto.getTitle());
            preparedStatement.setString(2,dto.getQuestion());
            preparedStatement.setString(3,dto.getAnswer());
            preparedStatement.setInt(4,dto.getLevel());
            preparedStatement.setBoolean(5,dto.isPublic());
            preparedStatement.setInt(6,dto.getUser().getId());
            preparedStatement.setInt(7,dto.getCategory().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0 ) throw new SQLException("Creating Flashcard failed.");

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dto.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating Flashcard failed, no ID obtained.");
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
