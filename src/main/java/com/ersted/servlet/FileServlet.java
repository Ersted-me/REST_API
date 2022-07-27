package com.ersted.servlet;

import com.ersted.controller.EventController;
import com.ersted.controller.FileController;
import com.ersted.controller.UserController;
import com.ersted.controller.impl.EventControllerImpl;
import com.ersted.controller.impl.FileControllerImpl;
import com.ersted.controller.impl.UserControllerImpl;
import com.ersted.model.Action;
import com.ersted.model.Event;
import com.ersted.model.File;
import com.ersted.model.User;
import com.ersted.repository.impl.EventRepositoryImpl;
import com.ersted.repository.impl.FileRepositoryImpl;
import com.ersted.repository.impl.UserRepositoryImpl;
import com.ersted.service.impl.EventServiceImpl;
import com.ersted.service.impl.FileServiceImpl;
import com.ersted.service.impl.UserServiceImpl;
import com.ersted.util.FilesUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "fileServlet", urlPatterns = {"/file", "/file/*"})
public class FileServlet extends HttpServlet {

    private FileController fileController;
    private UserController userController;
    private EventController eventController;

    // uri = /file or /file/
    private final String regexFiles = "\\/file+[\\/]|\\/file";

    // uri = /file/{id} or /file/{id}/
    private final String regexFile = "\\/file\\/+\\d+$|\\/file\\/+\\d++[\\/]";

    @Override
    public void init() throws ServletException {
        fileController = new FileControllerImpl(new FileServiceImpl(new FileRepositoryImpl()));
        userController = new UserControllerImpl(new UserServiceImpl(new UserRepositoryImpl()));
        eventController = new EventControllerImpl(new EventServiceImpl(new EventRepositoryImpl()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getRequestURI().matches(regexFiles)) {
            List<File> files = fileController.getAll();
            resp.getWriter().write(files.toString());
        }

        if (req.getRequestURI().matches(regexFile)) {
            String[] uriParts = req.getRequestURI().split("/");

            User user = userController.getByLogin(req.getParameter("login"));

            if (user == null)
                throw new ServletException("Invalid login.");


            Long fileId = Long.parseLong(uriParts[2]);
            File file = fileController.getById(fileId);

            if (file == null)
                throw new ServletException("File not found.");
            else{
                FilesUtil.sendFile(resp, file);
                Event event = new Event(null, user, file, new Date(), Action.DOWNLOAD, null);
                eventController.create(event);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!req.getRequestURI().matches(regexFiles))
            return;

        User user = userController.getByLogin(req.getParameter("login"));

        if (user == null)
            throw new ServletException("Invalid login.");

        List<File> files = FilesUtil.loadFile(req);
        for (File file : files) {
            file.setUser(user);
            file = fileController.create(file);
            Event event = new Event(null, user, file, new Date(), Action.LOAD, null);
            eventController.create(event);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!req.getRequestURI().matches(regexFile))
            return;

        String[] uriParts = req.getRequestURI().split("/");
        String userLogin = req.getParameter("login");

        User user = userController.getByLogin(userLogin);

        if (user == null)
            throw new ServletException("Invalid login.");

        Long fileId = Long.parseLong(uriParts[2]);
        boolean isDeleted = fileController.deleteById(fileId);

        if (isDeleted){
            resp.getWriter().write("File deleted.");
            File file = new File();
            file.setId(fileId);
            Event event = new Event(null, user, file, new Date(), Action.DELETE, null);
            eventController.create(event);
        } else
            throw new ServletException("File not found.");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!req.getRequestURI().matches(regexFile))
            return;

        String[] uriParts = req.getRequestURI().split("/");
        String userLogin = req.getParameter("login");
        String updateName = req.getParameter("newfilename");

        User user = userController.getByLogin(userLogin);

        if (user == null)
            throw new ServletException("Invalid login.");

        File file = fileController.getById(Long.parseLong(uriParts[2]));

        if (file != null) {
            file.setName(updateName);
            fileController.update(file);
            Event event = new Event(null, user, file, new Date(), Action.UPDATE, null);
            eventController.create(event);
            resp.getWriter().write("File updated.");
        } else
            throw new ServletException("File not found.");
    }
}
