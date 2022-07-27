package com.ersted.service.impl;

import com.ersted.model.Event;
import com.ersted.model.Status;
import com.ersted.repository.EventRepository;
import com.ersted.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {
    private final EventRepository repository;

    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event create(Event obj) {
        return repository.create(obj);
    }

    @Override
    public Event getById(Long id) {
        Event event = repository.getById(id);
        if(event == null || event.getStatus().equals(Status.DELETED))
            return null;
        return event;
    }

    @Override
    public Event update(Event obj) {
        return repository.update(obj);
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public List<Event> getAll() {
        return repository.getAll()
                .stream()
                .filter(event -> event.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
