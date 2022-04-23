package pl.gredel.flashcards.servlets.flashcards;

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
@WebServlet("/flashcards/add")
public class AddFlashcardServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger( AddFlashcardServlet.class.getName() );

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String question = req.getParameter("question");
        String answer = req.getParameter("answer");
        String categoryReq = req.getParameter("category");
        String isPublicReq = req.getParameter("isPublic");
        String username = req.getSession().getAttribute("username").toString();
        int category = Integer.parseInt(categoryReq);
        boolean isPublic = Boolean.parseBoolean(isPublicReq);

        FlashcardService flashcardService = new FlashcardService();

        try {
            flashcardService.addFlashcard(title, question, answer, category, isPublic, username);
            resp.sendRedirect("/flashcards");
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            String failureMessage = e.getMessage();
            req.setAttribute("error",failureMessage);
            req.getRequestDispatcher("/html/flashcards.jsp").forward(req,resp);
        }
    }
}
