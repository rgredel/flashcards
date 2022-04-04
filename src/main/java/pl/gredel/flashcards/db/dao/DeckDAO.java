package pl.gredel.flashcards.db.dao;

import pl.gredel.flashcards.db.dao.util.DataAccessObject;
import pl.gredel.flashcards.model.Deck;
import pl.gredel.flashcards.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeckDAO  extends DataAccessObject<Deck> {

    private static final String INSERT = "INSERT INTO Deck(name, user_id) VALUES (?, ?)";

    private static final String FIND_BY_ID = "SELECT id, name, user_id FROM Deck WHERE id=?";
    private static final String FIND_ALL = "SELECT id, name, user_id FROM Deck";
    private static final String LAST_ID = "SELECT max(ID) FROM Deck";
    private static final String UPDATE = "UPDATE Deck SET name =? , user_id =? WHERE id=?";
    private static final String DELETE = "DELETE FROM Deck WHERE id=?";
    private static final String ADD_FLASHCARD_TO_DECK = "INSERT INTO deck_flashcard( deck_id , flashcard_id) VALUES (?, ?)";
    private static final String DELETE_FLASHCARD_FROM_DECK = "DELETE FROM deck_flashcard WHERE deck_id=? AND flashcard_id=?";

    @Override
    public Deck findById(int id) {
        Deck deck = new Deck();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                deck.setId(resultSet.getInt(1));
                deck.setName(resultSet.getString(2));
                UsersDAO usersDAO = new UsersDAO();
                Users user = usersDAO.findById(resultSet.getInt(3));
                deck.setUser(user);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return deck;
    }

    @Override
    public List<Deck> findAll() {
        List<Deck> decks = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Deck deck = new Deck();
                deck.setId(resultSet.getInt(1));
                deck.setName(resultSet.getString(2));
                UsersDAO usersDAO = new UsersDAO();
                Users user = usersDAO.findById(resultSet.getInt(3));
                deck.setUser(user);
                decks.add(deck);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return decks;
    }

    @Override
    public Deck update(Deck dto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,dto.getName());
            preparedStatement.setInt(2,dto.getUser().getId());
            preparedStatement.setInt(3,dto.getId());
            preparedStatement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return findById(dto.getId());
    }

    @Override
    public Deck create(Deck dto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,dto.getName());
            preparedStatement.setInt(2,dto.getUser().getId());
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
    public void addFlashcardToDeck(int idFlashcard, int idDeck){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_FLASHCARD_TO_DECK);
            preparedStatement.setInt(1,idDeck);
            preparedStatement.setInt(2,idFlashcard);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
    }
    public void deleteFlashcardFromDeck(int idFlashcard, int idDeck){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FLASHCARD_FROM_DECK);
            preparedStatement.setInt(1, idDeck);
            preparedStatement.setInt(2, idFlashcard);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
    }
}
