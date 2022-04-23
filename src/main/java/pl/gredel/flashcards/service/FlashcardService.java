package pl.gredel.flashcards.service;

import pl.gredel.flashcards.db.dao.FlashcardDAO;
import pl.gredel.flashcards.db.dao.UsersDAO;
import pl.gredel.flashcards.db.dao.util.DAOException;
import pl.gredel.flashcards.model.Category;
import pl.gredel.flashcards.model.Flashcard;
import pl.gredel.flashcards.model.Users;
import pl.gredel.flashcards.service.util.ServiceException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlashcardService {
    private static final Logger LOGGER = Logger.getLogger( FlashcardService.class.getName() );
    FlashcardDAO flashcardDAO = new FlashcardDAO();

    public List<Flashcard> getAllFlashcards() throws ServiceException {

        try {
            List<Flashcard> all = flashcardDAO.findAll();
            return all;
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error.", e);
        }
    }

    public void addFlashcard(String title, String question, String answer, int categoryId, boolean isPublic, String username) throws ServiceException {
        UserService userService = new UserService();
        Users user = userService.getUserByLogin(username);
        Category category = new Category(categoryId);
        Flashcard flashcard = new Flashcard(title,question,answer,0,isPublic,user,category);
        try {
            flashcardDAO.create(flashcard);
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error! Cannot add flashcard.", e);
        }
    }
    public void updateFlashcard(int id, String title, String question, String answer, int categoryId, boolean isPublic, String username, int level) throws ServiceException {
        UserService userService = new UserService();
        Users user = userService.getUserByLogin(username);
        Category category = new Category(categoryId);
        Flashcard flashcard = new Flashcard(id, title,question,answer,level,isPublic,user,category);
        try {
            flashcardDAO.update(flashcard);
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error! Cannot add flashcard.", e);
        }
    }

    public void delete(int flashcardId) throws ServiceException {
        try {
            flashcardDAO.delete(flashcardId);
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error! Cannot delete flashcard.", e);
        }
    }

    public List<Flashcard> getAllFlashcardsByUsername(String username) throws ServiceException {

        UserService userService = new UserService();
        Users user = userService.getUserByLogin(username);

        try {
            List<Flashcard> flashcards = flashcardDAO.findAllByUserId(user.getId());
            return flashcards;
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error.", e);
        }
    }

    public List<Flashcard> getAllPublicFlashcards() throws ServiceException {
        try {
            List<Flashcard> flashcards = flashcardDAO.findAllPublic();
            return flashcards;
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error.", e);
        }
    }
}
