package pl.gredel.flashcards.service;

import pl.gredel.flashcards.db.dao.FlashcardDAO;
import pl.gredel.flashcards.db.dao.UsersDAO;
import pl.gredel.flashcards.db.dao.util.DAOException;
import pl.gredel.flashcards.model.Users;
import pl.gredel.flashcards.service.util.ServiceException;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    private static final Logger LOGGER = Logger.getLogger( UserService.class.getName() );

    UsersDAO usersDAO = new UsersDAO();

    public boolean register(String login, String password, String email) throws ServiceException {

        try {
            Optional<Users> userFromDB = usersDAO.findByLogin(login);
            if(userFromDB.isPresent())
                throw new ServiceException("User already exists!");

            Users user = new Users(login, password, email);
            usersDAO.create(user);
            return true;
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error. Please try again.", e);
        }


    }

    public boolean login(String login, String password) throws ServiceException {
        Optional<Users> userFromDB = null;
        try {
            userFromDB = usersDAO.findByLogin(login);
        } catch (DAOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServiceException("Unexpected error. Please try again.", e);
        }
        if(!userFromDB.isPresent())
            throw new ServiceException("User don't exists!");

        if (userFromDB.get().getPassword().equals(password)) return true;
        else throw new ServiceException("Incorrect password!");
    }
}
