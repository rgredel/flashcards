package pl.gredel.flashcards.service;

import pl.gredel.flashcards.db.dao.CategoryDAO;
import pl.gredel.flashcards.db.dao.util.DAOException;
import pl.gredel.flashcards.model.Category;
import pl.gredel.flashcards.service.util.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryService {

    CategoryDAO categoryDAO = new CategoryDAO();
    private static final Logger LOGGER = Logger.getLogger( CategoryService.class.getName());

    public List<Category> getAllCategories() throws ServiceException {
        List<Category> categories = new ArrayList<>();
        try {
            categories = categoryDAO.findAll();
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error.", e);
        }
        return categories;
    }
}
