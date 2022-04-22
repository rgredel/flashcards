package pl.gredel.flashcards.service;

import pl.gredel.flashcards.db.dao.FlashcardDAO;
import pl.gredel.flashcards.db.dao.util.DAOException;
import pl.gredel.flashcards.model.Flashcard;
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

}
