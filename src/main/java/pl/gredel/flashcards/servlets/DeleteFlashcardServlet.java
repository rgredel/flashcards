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

@WebServlet("/flashcards/delete")
public class DeleteFlashcardServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger( DeleteFlashcardServlet.class.getName() );
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       int flashcardId = Integer.parseInt(req.getParameter("flashcardId"));
        FlashcardService flashcardService = new FlashcardService();
        try {
            flashcardService.delete(flashcardId);
            resp.sendRedirect("/flashcards");
        } catch (ServiceException e) {
            String failureMessage = e.getMessage();
            req.setAttribute("error",failureMessage);
            req.getRequestDispatcher("/html/flashcards.jsp").forward(req,resp);
        }
    }
}
