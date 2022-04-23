package pl.gredel.flashcards.filters;

import pl.gredel.flashcards.model.Category;
import pl.gredel.flashcards.model.Deck;
import pl.gredel.flashcards.service.CategoryService;
import pl.gredel.flashcards.service.DeckService;
import pl.gredel.flashcards.service.util.ServiceException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter("/*")
public class FlashcardsFilter implements Filter {
    private static Logger LOGGER = Logger.getLogger(FlashcardsFilter.class.getName() );

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)req;
        if(request.getRequestURI().startsWith("/flashcards")){
            HttpSession session = request.getSession();

            CategoryService categoryService = new CategoryService();
            DeckService deckService = new DeckService();
            try {
                List<Category> categories = categoryService.getAllCategories();
                req.setAttribute("categories", categories);

                String username = session.getAttribute("username").toString();
                List<Deck> decks = deckService.getAllDeckByUsername(username);
                req.setAttribute("decks", decks);
            } catch (ServiceException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }

        chain.doFilter(request, res);
    }

}
