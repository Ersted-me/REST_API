package com.ersted.servlet;

import com.ersted.controller.EventController;
import com.ersted.controller.impl.EventControllerImpl;
import com.ersted.model.Event;
import com.ersted.repository.impl.EventRepositoryImpl;
import com.ersted.service.impl.EventServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "eventServlet", urlPatterns = {"/event", "/event/*"})
public class EventServlet extends HttpServlet {
    private EventController eventController = null;

    // uri = /event or /event/
    private final String regexUsers = "\\/event+[\\/]|\\/event";

    // uri = /event/{id} or /event/{id}/
    private final String regexUser = "\\/event\\/+\\d+$|\\/event\\/+\\d++[\\/]";

    @Override
    public void init() {
        eventController = new EventControllerImpl(new EventServiceImpl(new EventRepositoryImpl()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().matches(regexUsers)) {
            List<Event> events = eventController.getAll();
            resp.getWriter().write(events.toString());
        }

        if (req.getRequestURI().matches(regexUser)) {
            String[] uriParts = req.getRequestURI().split("/");

            Event event = eventController.getById(Long.parseLong(uriParts[2]));

            if (event == null)
                throw new ServletException("Event not found.");
            else
                resp.getWriter().write(event.toString());
        }
    }
}
