package com.ersted.servlet;

import com.ersted.controller.UserController;
import com.ersted.controller.impl.UserControllerImpl;
import com.ersted.model.User;
import com.ersted.repository.impl.UserRepositoryImpl;
import com.ersted.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(name = "userServlet", urlPatterns = {"/user", "/user/*"})
public class UserServlet extends HttpServlet {
    private UserController userController = null;

    // uri = /user or /user/
    private final String regexUsers = "\\/user+[\\/]|\\/user";

    // uri = /user/{id} or /user/{id}/
    private final String regexUser = "\\/user\\/+\\d+$|\\/user\\/+\\d++[\\/]";

    @Override
    public void init() throws ServletException {
        userController = new UserControllerImpl(new UserServiceImpl(new UserRepositoryImpl()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (req.getRequestURI().matches(regexUsers)) {
            List<User> users = userController.getAll();
            resp.getWriter().write(users.toString());
        }

        if (req.getRequestURI().matches(regexUser)) {
            String[] uriParts = req.getRequestURI().split("/");

            User user = userController.getById(Long.parseLong(uriParts[2]));

            if (user == null)
                throw new ServletException("User not found.");
            else
                resp.getWriter().write(user.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(!req.getRequestURI().matches(regexUsers))
            return;

        String userLogin = req.getParameter("login");

        User user = userController.create(new User(null, userLogin, null, null));

        if (user == null)
            throw new ServletException("User not created.");
        else
            resp.getWriter().write(user.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!req.getRequestURI().matches(regexUser))
            return;

        String[] uriParts = req.getRequestURI().split("/");

        boolean isDeleted = userController.deleteById(Long.parseLong(uriParts[2]));

        if(isDeleted)
            resp.getWriter().write("User deleted.");
        else
            throw new ServletException("User not found.");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getRequestURI().matches(regexUser))
            return;
        String[] uriParts = req.getRequestURI().split("/");
        String updateLogin = req.getParameter("newlogin");

        User user = userController.getById(Long.parseLong(uriParts[2]));


        if(user != null) {
            resp.getWriter().write("User updated.");
            user.setLogin(updateLogin);
            userController.update(user);
        } else
            throw new ServletException("User not found.");
    }
}
