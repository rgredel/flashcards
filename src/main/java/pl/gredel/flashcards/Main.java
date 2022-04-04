package pl.gredel.flashcards;

import pl.gredel.flashcards.db.conf.ConnectionPool;
import pl.gredel.flashcards.db.dao.CategoryDAO;
import pl.gredel.flashcards.db.dao.DeckDAO;
import pl.gredel.flashcards.db.dao.FlashcardDAO;
import pl.gredel.flashcards.db.dao.UsersDAO;
import pl.gredel.flashcards.model.Category;
import pl.gredel.flashcards.model.Deck;
import pl.gredel.flashcards.model.Flashcard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // uncomment to create tables and add test data
//    DatabaseInitializer dbInit = new DatabaseInitializer();
//    dbInit.init();

            try {
                Connection connection = ConnectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
                while (resultSet.next()){
                    System.out.println(resultSet.getInt(1));
                    System.out.println(resultSet.getString(2));
                    System.out.println(resultSet.getString(3));
                    System.out.println(resultSet.getString(4));
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

        Category category = new Category();
        List<Category> categories;

        CategoryDAO categoryDAO = new CategoryDAO();

        category.setName("TEST");
        category = categoryDAO.create(category);
        categories = categoryDAO.findAll();
        categories.forEach(System.out::println);

        categoryDAO.delete(category.getId());
        category = categoryDAO.findById(1);
        category.setName("Reversed category");
        categoryDAO.update(category);

        categories = categoryDAO.findAll();
        categories.forEach(System.out::println);

        UsersDAO usersDAO = new UsersDAO();

        FlashcardDAO flashcardDAO = new FlashcardDAO();
        Flashcard flashcard = new Flashcard("IT","What is CPU?","Processor",0,false,usersDAO.findById(1),categoryDAO.findById(1));
        flashcardDAO.create(flashcard);

        List<Flashcard> allPublic = flashcardDAO.findAllByUserId(1);
        allPublic.forEach(System.out::println);

        DeckDAO deckDAO = new DeckDAO();
        Deck deck = deckDAO.findById(1);
        deck.setFlashcards(flashcardDAO.findAllByDeckId(deck.getId()));
        System.out.println(deck);
    }


}
