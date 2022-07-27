package com.ersted.service.impl;

import com.ersted.model.*;
import com.ersted.repository.EventRepository;
import com.ersted.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest {

    @Mock
    private EventRepository repository;
    private EventService service;
    private File file;
    private User user;
    private Date date;

    @Before
    public void setUp(){
        service = new EventServiceImpl(repository);
        user = new User(1L, "login", null, Status.ACTIVE);
        file = new File(1L, "name", "path", Status.ACTIVE, user);
        date = new Date();
    }

    @Test
    public void createEventWithoutIdThenReturnEventWithId() {
        Event event = new Event(null, user, file, date, Action.LOAD, Status.ACTIVE);
        Event expected = new Event(1L, user, file, date, Action.LOAD, Status.ACTIVE);

        when(repository.create(event)).thenReturn(expected);

        Event actual = service.create(event);

        assertEquals(expected, actual);
    }

    @Test
    public void createEventWithRandomIdThenReturnEventWithRightId() {

        Event event = new Event(999L, user, file, date, Action.LOAD, Status.ACTIVE);
        Event expected = new Event(1L, user, file, date, Action.LOAD, Status.ACTIVE);

        when(repository.create(event)).thenReturn(expected);

        Event actual = service.create(event);

        assertEquals(expected, actual);
    }

    @Test
    public void createEventWithWrongStatusThenReturnEventWithRightStatus() {
        Event event = new Event(999L, user, file, date, Action.LOAD, Status.DELETED);
        Event expected = new Event(1L, user, file, date, Action.LOAD, Status.ACTIVE);

        when(repository.create(event)).thenReturn(expected);

        Event actual = service.create(event);

        assertEquals(expected, actual);
    }

    @Test
    public void createEventWithNullableFieldThenReturnNull() {
        Event event = new Event(null, null, null, null, null, null);
        Event expected = null;

        when(repository.create(event)).thenReturn(expected);

        Event actual = service.create(event);

        assertEquals(expected, actual);
    }

    @Test
    public void getExistEventThenReturnEvent() {
        Long eventId = 1L;
        Event expected = new Event(1L, user, file, date, Action.LOAD, Status.ACTIVE);

        when(repository.getById(eventId)).thenReturn(expected);

        Event actual = service.getById(eventId);

        assertEquals(expected, actual);
    }

    @Test
    public void getNotExistEventThenReturnNull() {
        Long eventId = 1L;
        Event expected = null;

        when(repository.getById(eventId)).thenReturn(expected);

        Event actual = service.getById(eventId);

        assertEquals(expected, actual);
    }

    @Test
    public void getDeletedEventThenReturnNull() {
        Long eventId = 1L;
        Event event = new Event(1L, user, file, date, Action.LOAD, Status.DELETED);
        Event expected = null;

        when(repository.getById(eventId)).thenReturn(event);

        Event actual = service.getById(eventId);

        assertEquals(expected, actual);
    }

    @Test
    public void updateExistEventThenReturnUpdatedEvent() {
        Event event = new Event(1L, user, file, date, Action.LOAD, Status.ACTIVE);
        Event expected = new Event(1L, user, file, date, Action.LOAD, Status.ACTIVE);

        when(repository.update(event)).thenReturn(expected);

        Event actual = service.update(event);

        assertEquals(expected, actual);
    }

    @Test
    public void updateNotExistEventThenReturnNull() {
        Event event = new Event(1L, user, file, date, Action.LOAD, Status.ACTIVE);
        Event expected =null;

        when(repository.update(event)).thenReturn(expected);

        Event actual = service.update(event);

        assertEquals(expected, actual);
    }

    @Test
    public void updateEventWithNullIdThenReturnNull() {
        Event event = new Event(null, user, file, date, Action.LOAD, Status.ACTIVE);
        Event expected =null;

        when(repository.update(event)).thenReturn(null);

        Event actual = service.update(event);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteExistEventThenReturnTrue() {
        Long eventId = 1L;
        boolean expected = true;

        when(repository.deleteById(eventId)).thenReturn(expected);

        boolean actual = service.deleteById(eventId);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteNotExistEventThenReturnFalse() {
        Long eventId = 1L;
        boolean expected = false;

        when(repository.deleteById(eventId)).thenReturn(expected);

        boolean actual = service.deleteById(eventId);

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithOneEventThenReturnSingletonList() {
        Event event = new Event(null, user, file, date, Action.LOAD, Status.ACTIVE);
        List<Event> expected = Collections.singletonList(event);

        when(repository.getAll()).thenReturn(expected);

        List<Event> actual = service.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithoutEventThenReturnEmptyList() {
        List<Event> expected = Collections.emptyList();

        when(repository.getAll()).thenReturn(expected);

        List<Event> actual = service.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithEventsThenReturnOnlyActiveEvents() {
        Event activeEvent = new Event(null, user, file, date, Action.LOAD, Status.ACTIVE);
        Event deletedEvent = new Event(null, user, file, date, Action.LOAD, Status.DELETED);
        List<Event> users = Arrays.asList(activeEvent, deletedEvent);

        List<Event> expected = Collections.singletonList(activeEvent);

        when(repository.getAll()).thenReturn(users);

        List<Event> actual = service.getAll();

        assertEquals(expected, actual);
    }

}