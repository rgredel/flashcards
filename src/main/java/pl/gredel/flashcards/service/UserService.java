package pl.gredel.flashcards.service;

import pl.gredel.flashcards.db.dao.UsersDAO;
import pl.gredel.flashcards.db.dao.util.DAOException;
import pl.gredel.flashcards.model.Users;

import java.util.Optional;

public class UserService {
    UsersDAO usersDAO = new UsersDAO();

    boolean register(String login, String password, String email) throws DAOException {
        UsersDAO usersDAO = new UsersDAO();

        Optional<Users> userFromDB = usersDAO.findByLogin(login);
        if(userFromDB.isPresent()) return false;

        Users user = new Users(login, password, email);
        usersDAO.create(user);

        return true;
    }
    
}
