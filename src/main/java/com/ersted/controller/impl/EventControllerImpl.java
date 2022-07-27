package com.ersted.controller.impl;

import com.ersted.controller.EventController;
import com.ersted.model.Event;
import com.ersted.service.EventService;

import java.util.List;

public class EventControllerImpl implements EventController {
    private final EventService eventController;

    public EventControllerImpl(EventService eventController) {
        this.eventController = eventController;
    }

    @Override
    public Event create(Event obj) {
        return eventController.create(obj);
    }

    @Override
    public Event getById(Long id) {
        return eventController.getById(id);
    }

    @Override
    public Event update(Event obj) {
        return eventController.update(obj);
    }

    @Override
    public boolean deleteById(Long id) {
        return eventController.deleteById(id);
    }

    @Override
    public List<Event> getAll() {
        return eventController.getAll();
    }
}
