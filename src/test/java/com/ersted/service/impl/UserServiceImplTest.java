package com.ersted.service.impl;

import com.ersted.model.Status;
import com.ersted.model.User;
import com.ersted.repository.UserRepository;
import com.ersted.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository repository;
    private UserService service;

    @Before
    public void setUp() {
        service = new UserServiceImpl(repository);
    }

    @Test
    public void createUserWithoutIdThenReturnUserWithId() {
        User user = new User(null, "test", null, Status.ACTIVE);
        User expected = new User(1L, "test", null, Status.ACTIVE);

        when(repository.create(user)).thenReturn(expected);

        User actual = service.create(user);

        assertEquals(expected, actual);
    }

    @Test
    public void createUserWithRandomIdThenReturnUserWithRightId() {
        User user = new User(9999L, "test", null, Status.ACTIVE);
        User expected = new User(1L, "test", null, Status.ACTIVE);

        when(repository.create(user)).thenReturn(expected);

        User actual = service.create(user);

        assertEquals(expected, actual);
    }

    @Test
    public void createUserWithWrongStatusThenReturnUserWithRightStatus() {
        User user = new User(null, "test", null, null);
        User expected = new User(1L, "test", null, Status.ACTIVE);

        when(repository.create(user)).thenReturn(expected);

        User actual = service.create(user);

        assertEquals(expected, actual);
    }


    @Test
    public void createUserWithoutLoginThenReturnNull() {
        User user = new User(null, null, null, Status.ACTIVE);
        User expected = null;

        when(repository.create(user)).thenReturn(expected);

        User actual = service.create(user);

        assertEquals(expected, actual);
    }

    @Test
    public void getExistUserThenReturnUser() {
        Long userId = 1L;
        User expected = new User(1L, "test", null, Status.ACTIVE);

        when(repository.getById(userId)).thenReturn(expected);

        User actual = service.getById(userId);

        assertEquals(expected, actual);
    }

    @Test
    public void getNotExistUserThenReturnNull() {
        Long userId = 1L;
        User expected = null;

        when(repository.getById(userId)).thenReturn(expected);

        User actual = service.getById(userId);

        assertEquals(expected, actual);
    }

    @Test
    public void getDeletedUserThenReturnNull() {
        Long userId = 1L;
        User user = new User(1L, "test", null, Status.DELETED);
        User expected = null;

        when(repository.getById(userId)).thenReturn(user);

        User actual = service.getById(userId);

        assertEquals(expected, actual);
    }

    @Test
    public void updateExistUserThenReturnUpdatedUser() {
        User user = new User(1L, "test", null, Status.ACTIVE);
        User expected = new User(1L, "test", null, Status.ACTIVE);

        when(repository.update(user)).thenReturn(expected);

        User actual = service.update(user);

        assertEquals(expected, actual);
    }

    @Test
    public void updateNotExistUserThenReturnNull() {
        User user = new User(1L, "test", null, Status.ACTIVE);
        User expected = null;

        when(repository.update(user)).thenReturn(null);

        User actual = service.update(user);

        assertEquals(expected, actual);
    }

    @Test
    public void updateUserWithNullIdThenReturnNull() {
        User user = new User(null, "test", null, Status.ACTIVE);
        User expected = null;

        when(repository.update(user)).thenReturn(null);

        User actual = service.update(user);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteExistUserThenReturnTrue() {
        Long userId = 1L;
        boolean expected = true;

        when(repository.deleteById(userId)).thenReturn(expected);

        boolean actual = service.deleteById(userId);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteNotExistUserThenReturnFalse() {
        Long userId = 1L;
        boolean expected = false;

        when(repository.deleteById(userId)).thenReturn(expected);

        boolean actual = service.deleteById(userId);

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithOneUserThenReturnSingletonList() {
        User user = new User(1L, "test", null, Status.ACTIVE);
        List<User> expected = Collections.singletonList(user);

        when(repository.getAll()).thenReturn(expected);

        List<User> actual = service.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithoutUsersThenReturnEmptyList() {
        List<User> expected = Collections.emptyList();

        when(repository.getAll()).thenReturn(expected);

        List<User> actual = service.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithUsersThenReturnOnlyActiveUsers() {
        User activeUser = new User(1L, "activeUser", null, Status.ACTIVE);
        User deletedUser = new User(1L, "activeUser", null, Status.DELETED);
        List<User> users = Arrays.asList(activeUser, deletedUser);

        List<User> expected = Collections.singletonList(activeUser);

        when(repository.getAll()).thenReturn(users);

        List<User> actual = service.getAll();

        assertEquals(expected, actual);
    }

}