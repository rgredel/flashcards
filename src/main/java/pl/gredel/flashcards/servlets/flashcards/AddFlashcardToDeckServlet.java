package pl.gredel.flashcards.servlets.flashcards;

import pl.gredel.flashcards.service.DeckService;
import pl.gredel.flashcards.service.FlashcardService;
import pl.gredel.flashcards.service.util.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/flashcards/addToDeck")
public class AddFlashcardToDeckServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AddFlashcardToDeckServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int deckId = Integer.parseInt(req.getParameter("deckId"));
        int flashcardId = Integer.parseInt(req.getParameter("flashcardId"));
        String redirectPath = req.getParameter("redirectPath");
        DeckService deckService = new DeckService();
        try {
            deckService.addFlashcardToDeck(flashcardId, deckId);
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            String failureMessage = e.getMessage();
            req.setAttribute("error", failureMessage);
        }finally {
            if (redirectPath == null) {
                resp.sendRedirect("/flashcards");
            }else{
                resp.sendRedirect(redirectPath);
            }
        }
    }
}