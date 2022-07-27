package com.ersted.service.impl;

import com.ersted.model.File;
import com.ersted.model.Status;
import com.ersted.model.User;
import com.ersted.repository.FileRepository;
import com.ersted.service.FileService;
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
public class FileRepositoryImplTest {
    @Mock
    private FileRepository repository;
    private FileService service;

    private User user;

    @Before
    public void setUp() {
        service = new FileServiceImpl(repository);
        user = new User(1L, "test", null, Status.ACTIVE);
    }

    @Test
    public void createFileWithoutIdThenReturnFileWithId() {
        File file = new File(null, "name", "path", Status.ACTIVE, user);
        File expected = new File(1L, "name", "path", Status.ACTIVE, user);

        when(repository.create(file)).thenReturn(expected);

        File actual = service.create(file);

        assertEquals(expected, actual);
    }

    @Test
    public void createFileWithRandomIdThenReturnFileWithRightId() {
        File file = new File(999L, "name", "path", Status.ACTIVE, user);
        File expected = new File(1L, "name", "path", Status.ACTIVE, user);

        when(repository.create(file)).thenReturn(expected);

        File actual = service.create(file);

        assertEquals(expected, actual);
    }

    @Test
    public void createFileWithWrongStatusThenReturnFileWithRightStatus() {
        File file = new File(999L, "name", "path", Status.DELETED, user);
        File expected = new File(1L, "name", "path", Status.ACTIVE, user);

        when(repository.create(file)).thenReturn(expected);

        File actual = service.create(file);

        assertEquals(expected, actual);
    }

    @Test
    public void createFileWithNullableFieldThenReturnNull() {
        File file = new File(null, null, null, null, null);
        File expected = null;

        when(repository.create(file)).thenReturn(expected);

        File actual = service.create(file);

        assertEquals(expected, actual);
    }

    @Test
    public void getExistFileThenReturnFile() {
        Long fileId = 1L;
        File expected = new File(999L, "name", "path", Status.ACTIVE, user);

        when(repository.getById(fileId)).thenReturn(expected);

        File actual = service.getById(fileId);

        assertEquals(expected, actual);
    }

    @Test
    public void getNotExistFileThenReturnNull() {
        Long fileId = 1L;
        File expected = null;

        when(repository.getById(fileId)).thenReturn(expected);

        File actual = service.getById(fileId);

        assertEquals(expected, actual);
    }

    @Test
    public void getDeletedFileThenReturnNull() {
        Long fileId = 1L;
        File file = new File(1L, "name", "path", Status.DELETED, user);
        File expected = null;

        when(repository.getById(fileId)).thenReturn(file);

        File actual = service.getById(fileId);

        assertEquals(expected, actual);
    }

    @Test
    public void updateExistFileThenReturnUpdatedFile() {
        File file = new File(1L, "name", "path", Status.ACTIVE, user);
        File expected = new File(1L, "name", "path", Status.ACTIVE, user);

        when(repository.update(file)).thenReturn(expected);

        File actual = service.update(file);

        assertEquals(expected, actual);
    }

    @Test
    public void updateNotExistFileThenReturnNull() {
        File file = new File(1L, "name", "path", Status.ACTIVE, user);
        File expected = null;

        when(repository.update(file)).thenReturn(null);

        File actual = service.update(file);

        assertEquals(expected, actual);
    }

    @Test
    public void updateFileWithNullIdThenReturnNull() {
        File file = new File(null, "name", "path", Status.ACTIVE, user);
        File expected = null;

        when(repository.update(file)).thenReturn(null);

        File actual = service.update(file);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteExistFileThenReturnTrue() {
        Long fileId = 1L;
        boolean expected = true;

        when(repository.deleteById(fileId)).thenReturn(expected);

        boolean actual = service.deleteById(fileId);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteNotExistFileThenReturnFalse() {
        Long fileId = 1L;
        boolean expected = false;

        when(repository.deleteById(fileId)).thenReturn(expected);

        boolean actual = service.deleteById(fileId);

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithOneFileThenReturnSingletonList() {
        File file = new File(null, "name", "path", Status.ACTIVE, user);
        List<File> expected = Collections.singletonList(file);

        when(repository.getAll()).thenReturn(expected);

        List<File> actual = service.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithoutFileThenReturnEmptyList() {
        List<File> expected = Collections.emptyList();

        when(repository.getAll()).thenReturn(expected);

        List<File> actual = service.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void getListWithFilesThenReturnOnlyActiveFiles() {
        File activeUser = new File(null, "name", "path", Status.ACTIVE, user);
        File deletedUser = new File(null, "name", "path", Status.DELETED, user);
        List<File> users = Arrays.asList(activeUser, deletedUser);

        List<File> expected = Collections.singletonList(activeUser);

        when(repository.getAll()).thenReturn(users);

        List<File> actual = service.getAll();

        assertEquals(expected, actual);
    }

}