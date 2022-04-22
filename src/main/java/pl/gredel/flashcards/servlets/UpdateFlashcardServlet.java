package pl.gredel.flashcards.servlets;

import pl.gredel.flashcards.service.FlashcardService;
import pl.gredel.flashcards.service.util.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/flashcards/update")
public class UpdateFlashcardServlet extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger( UpdateFlashcardServlet.class.getName() );

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int flashcardId = Integer.parseInt(req.getParameter("flashcardId"));
        String title = req.getParameter("newTitle");
        String question = req.getParameter("newQuestion");
        String answer = req.getParameter("newAnswer");
        int category = Integer.parseInt(req.getParameter("newCategory"));
        int level = Integer.parseInt(req.getParameter("newLevel"));
        boolean isPublic = Boolean.parseBoolean(req.getParameter("newIsPublic"));
        String username = req.getSession().getAttribute("username").toString();
        FlashcardService flashcardService = new FlashcardService();
        try {
            flashcardService.updateFlashcard(flashcardId,title, question, answer, category, isPublic, username , level);
            resp.sendRedirect("/flashcards");
        } catch (ServiceException e) {
            String failureMessage = e.getMessage();
            req.setAttribute("error",failureMessage);
            req.getRequestDispatcher("/html/flashcards.jsp").forward(req,resp);
        }

    }
}
