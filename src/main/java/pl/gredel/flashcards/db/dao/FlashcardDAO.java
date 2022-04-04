package pl.gredel.flashcards.db.dao;

import pl.gredel.flashcards.db.dao.util.DataAccessObject;
import pl.gredel.flashcards.model.Flashcard;
import pl.gredel.flashcards.model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlashcardDAO extends DataAccessObject<Flashcard> {
    private static final String INSERT = "INSERT INTO Flashcard(title, question, answer, level, is_public, user_id, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM Flashcard WHERE id=?";
    private static final String FIND_ALL = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM flashcard";
    private static final String LAST_ID = "SELECT max(ID) FROM Flashcard";
    private static final String UPDATE = "UPDATE Flashcard SET title = ?, question = ?, answer = ?, level = ?, is_public = ?, user_id = ?, category_id = ? WHERE id=?";
    private static final String DELETE = "DELETE FROM Flashcard WHERE id=?";
    private static final String FIND_ALL_PUBLIC = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM flashcard WHERE is_public='t'";
    private static final String FIND_ALL_BY_USER = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM flashcard WHERE user_id=?";
    private static final String FIND_ALL_BY_DECK = "SELECT id, title, question, answer, level, is_public, user_id, category_id FROM flashcard join deck_flashcard on id = flashcard_id WHERE user_id=?";


    @Override
    public Flashcard findById(int id) {
        Flashcard flashcard = new Flashcard();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                flashcard.setId(resultSet.getInt(1));
                flashcard.setTitle(resultSet.getString(2));
                flashcard.setQuestion(resultSet.getString(3));
                flashcard.setAnswer(resultSet.getString(4));
                flashcard.setLevel(resultSet.getInt(5));
                flashcard.setPublic(resultSet.getBoolean(6));
                UsersDAO usersDAO = new UsersDAO();
                Users user = usersDAO.findById(resultSet.getInt(7));
                CategoryDAO categoryDAO = new CategoryDAO();
                flashcard.setCategory(categoryDAO.findById(resultSet.getInt(8)));
                flashcard.setUser(user);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return flashcard;
    }


    @Override
    public List<Flashcard> findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            return findAllTemplate(preparedStatement);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
    }

    public List<Flashcard> findAllPublic() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PUBLIC);
            return findAllTemplate(preparedStatement);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }

    }
    public List<Flashcard> findAllByDeckId(int idDeck) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DECK);
            preparedStatement.setInt(1, idDeck);
            return findAllTemplate(preparedStatement);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }

    }

    public List<Flashcard> findAllByUserId(int id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USER);
            preparedStatement.setInt(1, id);
            return findAllTemplate(preparedStatement);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }

    }

    private List<Flashcard> findAllTemplate(PreparedStatement preparedStatement) {
        List<Flashcard> flashcards = new ArrayList<>();

        try {
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
                Users user = usersDAO.findById(resultSet.getInt(7));
                CategoryDAO categoryDAO = new CategoryDAO();
                flashcard.setCategory(categoryDAO.findById(resultSet.getInt(8)));
                flashcard.setUser(user);
                flashcards.add(flashcard);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return flashcards;
    }

    @Override
    public Flashcard update(Flashcard dto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,dto.getTitle());
            preparedStatement.setString(2,dto.getQuestion());
            preparedStatement.setString(3,dto.getAnswer());
            preparedStatement.setInt(4,dto.getLevel());
            preparedStatement.setBoolean(5,dto.isPublic());
            preparedStatement.setInt(6,dto.getUser().getId());
            preparedStatement.setInt(7,dto.getCategory().getId());
            preparedStatement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return findById(dto.getId());
    }

    @Override
    public Flashcard create(Flashcard dto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,dto.getTitle());
            preparedStatement.setString(2,dto.getQuestion());
            preparedStatement.setString(3,dto.getAnswer());
            preparedStatement.setInt(4,dto.getLevel());
            preparedStatement.setBoolean(5,dto.isPublic());
            preparedStatement.setInt(6,dto.getUser().getId());
            preparedStatement.setInt(7,dto.getCategory().getId());
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
