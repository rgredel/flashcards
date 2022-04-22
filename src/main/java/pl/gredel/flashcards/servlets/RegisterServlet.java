package pl.gredel.flashcards.servlets;

import pl.gredel.flashcards.service.UserService;
import pl.gredel.flashcards.service.util.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String repeatPassword = req.getParameter("repeatedPassword");

        String failureMessage;

        if(password.equals(repeatPassword)){
            UserService userService = new UserService();
            try {
                userService.register(username, password, email);
                req.getRequestDispatcher("/login").forward(req,resp);
            } catch (ServiceException e) {
                failureMessage = e.getMessage();
                req.setAttribute("error",failureMessage);
                req.getRequestDispatcher("/html/register.jsp").forward(req,resp);
            }
        }
        else{
        req.setAttribute("error","Passwords do not match.");
        req.getRequestDispatcher("/html/register.jsp").forward(req,resp);
        }
    }
}
