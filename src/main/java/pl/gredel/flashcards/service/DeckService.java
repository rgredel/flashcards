package pl.gredel.flashcards.service;

import pl.gredel.flashcards.db.dao.DeckDAO;
import pl.gredel.flashcards.db.dao.util.DAOException;
import pl.gredel.flashcards.model.Deck;
import pl.gredel.flashcards.model.Flashcard;
import pl.gredel.flashcards.model.Users;
import pl.gredel.flashcards.service.util.ServiceException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeckService {
    private static final Logger LOGGER = Logger.getLogger( DeckService.class.getName() );
    private DeckDAO deckDAO = new DeckDAO();

    public void addFlashcardToDeck(int flashcardID, int deckId) throws ServiceException {
        try {
            deckDAO.addFlashcardToDeck(flashcardID,deckId);
        } catch (DAOException e) {
            throw new ServiceException("Cannot add flashcard to deck",e);
        }
    }
    public List<Deck> getAllDeckByUsername(String username) throws ServiceException {

        UserService userService = new UserService();
        Users user = userService.getUserByLogin(username);

        try {
            List<Deck> decks = deckDAO.findAllByUserId(user.getId());
            return decks;
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error.", e);
        }
    }
}
