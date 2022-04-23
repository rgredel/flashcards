package pl.gredel.flashcards.servlets.flashcards;

import pl.gredel.flashcards.service.FlashcardService;
import pl.gredel.flashcards.service.util.ServiceException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/flashcards/delete")
public class DeleteFlashcardServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger( DeleteFlashcardServlet.class.getName() );
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       int flashcardId = Integer.parseInt(req.getParameter("flashcardId"));
        FlashcardService flashcardService = new FlashcardService();
        try {
            flashcardService.delete(flashcardId);
            resp.sendRedirect("/flashcards");
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            resp.sendRedirect("/flashcards");
        }
    }
}
