package pl.gredel.flashcards.db.dao;

import pl.gredel.flashcards.db.conf.ConnectionPool;
import pl.gredel.flashcards.db.dao.util.DataAccessObject;
import pl.gredel.flashcards.model.Deck;
import pl.gredel.flashcards.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeckDAO  extends DataAccessObject<Deck> {

    private static final Logger LOGGER = Logger.getLogger( DeckDAO.class.getName() );

    private static final String INSERT = "INSERT INTO Deck(name, user_id) VALUES (?, ?)";
    private static final String FIND_BY_ID = "SELECT id, name, user_id FROM Deck WHERE id=?";
    private static final String FIND_ALL = "SELECT id, name, user_id FROM Deck";
    private static final String UPDATE = "UPDATE Deck SET name =? , user_id =? WHERE id=?";
    private static final String DELETE = "DELETE FROM Deck WHERE id=?";
    private static final String ADD_FLASHCARD_TO_DECK = "INSERT INTO deck_flashcard( deck_id , flashcard_id) VALUES (?, ?)";
    private static final String DELETE_FLASHCARD_FROM_DECK = "DELETE FROM deck_flashcard WHERE deck_id=? AND flashcard_id=?";

    @Override
    public Optional<Deck> findById(int id) {
        Deck deck = null;
         try(Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID) ){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                deck = new Deck();
                deck.setId(resultSet.getInt(1));
                deck.setName(resultSet.getString(2));
                UsersDAO usersDAO = new UsersDAO();
                Users user = usersDAO.findById(resultSet.getInt(3)).get();
                deck.setUser(user);
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return Optional.ofNullable(deck);
    }

    @Override
    public List<Deck> findAll() {
        List<Deck> decks = new ArrayList<>();

        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Deck deck = new Deck();
                deck.setId(resultSet.getInt(1));
                deck.setName(resultSet.getString(2));
                UsersDAO usersDAO = new UsersDAO();
                Users user = usersDAO.findById(resultSet.getInt(3)).get();
                deck.setUser(user);
                decks.add(deck);
            }

        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return decks;
    }

    @Override
    public Deck update(Deck dto) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1,dto.getName());
            preparedStatement.setInt(2,dto.getUser().getId());
            preparedStatement.setInt(3,dto.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0 ) throw new SQLException("Update Deck failed.");

        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
        return findById(dto.getId()).get();
    }

    @Override
    public Deck create(Deck dto) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1,dto.getName());
            preparedStatement.setInt(2,dto.getUser().getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0 ) throw new SQLException("Creating Deck failed.");

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dto.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating Deck failed, no ID obtained.");
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
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0 ) throw new SQLException("Deleting failed.");
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
    }
    public void addFlashcardToDeck(int idFlashcard, int idDeck){
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_FLASHCARD_TO_DECK)) {
            preparedStatement.setInt(1,idDeck);
            preparedStatement.setInt(2,idFlashcard);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0 ) throw new SQLException("Adding Flashcard to Deck failed.");
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
    }
    public void deleteFlashcardFromDeck(int idFlashcard, int idDeck){
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FLASHCARD_FROM_DECK)){
            preparedStatement.setInt(1, idDeck);
            preparedStatement.setInt(2, idFlashcard);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0 ) throw new SQLException("Deleting Flashcard from Deck failed.");
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.toString(), sqlException);
        }
    }
}
