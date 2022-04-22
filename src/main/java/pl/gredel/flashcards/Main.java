package pl.gredel.flashcards;

import pl.gredel.flashcards.db.dao.CategoryDAO;
import pl.gredel.flashcards.db.dao.DeckDAO;
import pl.gredel.flashcards.db.dao.FlashcardDAO;
import pl.gredel.flashcards.db.dao.UsersDAO;
import pl.gredel.flashcards.db.dao.util.DAOException;
import pl.gredel.flashcards.model.Category;
import pl.gredel.flashcards.model.Deck;
import pl.gredel.flashcards.model.Flashcard;
import pl.gredel.flashcards.model.Users;
import pl.gredel.flashcards.service.UserService;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws Exception{

//        Category category = new Category();
//        List<Category> categories;
//
//        CategoryDAO categoryDAO = new CategoryDAO();
//
//        category.setName("TEST");
//        category = categoryDAO.create(category);
//        categories = categoryDAO.findAll();
//        categories.forEach(System.out::println);
//
//        categoryDAO.delete(category.getId());
//        category = categoryDAO.findById(1).get();
//        category.setName("Reversed category");
//        categoryDAO.update(category);
//
//        categories = categoryDAO.findAll();
//        categories.forEach(System.out::println);
//
//        UsersDAO usersDAO = new UsersDAO();
//
//        FlashcardDAO flashcardDAO = new FlashcardDAO();
//        Flashcard flashcard = new Flashcard("IT","What is CPU?","Processor",0,false,usersDAO.findById(1).get(),categoryDAO.findById(1).get());
//        flashcardDAO.create(flashcard);
//
//        List<Flashcard> allPublic = flashcardDAO.findAllByUserId(1);
//        allPublic.forEach(System.out::println);
//
//
//        DeckDAO deckDAO = new DeckDAO();
//        Deck deck = deckDAO.findById(1).get();
//        deckDAO.addFlashcardToDeck(2,1);
//        System.out.println(flashcardDAO.findAll());
//        deck.setFlashcards(flashcardDAO.findAllByDeckId(1));
//        System.out.println(deck);
//        System.out.println("___________PUBLIC__________");
//        deck.setFlashcards(flashcardDAO.findAllPublic());
//        System.out.println(deck);
//
//
//        deckDAO.deleteFlashcardFromDeck(2,deck.getId());
//        deck.setFlashcards(flashcardDAO.findAllByDeckId(deck.getId()));
//        System.out.println(deck);
//

    }
}
