package pl.gredel.flashcards.servlets;

import pl.gredel.flashcards.db.dao.FlashcardDAO;
import pl.gredel.flashcards.model.Flashcard;
import pl.gredel.flashcards.service.FlashcardService;
import pl.gredel.flashcards.service.util.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/flashcards")
public class FlashcardsServlet  extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FlashcardService.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FlashcardService flashcardService = new FlashcardService();

        try {
            List<Flashcard> flashcards = flashcardService.getAllFlashcards();
            req.setAttribute("flashcards", flashcards);
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        req.getRequestDispatcher("/html/flashcards.jsp").forward(req, resp);
    }

}