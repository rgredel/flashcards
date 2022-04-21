package pl.gredel.flashcards.servlets;


import pl.gredel.flashcards.service.UserService;
import pl.gredel.flashcards.service.util.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String failureMessage;

        UserService userService = new UserService();
        try {
            if (userService.login(username,password)){
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                req.getRequestDispatcher("/html/home.jsp").forward(req, resp);
            }
        } catch (ServiceException e) {
            failureMessage = e.getMessage();
            req.setAttribute("error",failureMessage);
            req.getRequestDispatcher("/html/login.jsp").forward(req,resp);
        }
    }
}
