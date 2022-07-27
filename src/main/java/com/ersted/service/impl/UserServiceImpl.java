package com.ersted.service.impl;

import com.ersted.model.Status;
import com.ersted.model.User;
import com.ersted.repository.UserRepository;
import com.ersted.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User obj) {
        return repository.create(obj);
    }

    @Override
    public User getById(Long id) {
        User user = repository.getById(id);
        if(user == null || user.getStatus().equals(Status.DELETED))
            return null;
        return user;
    }

    @Override
    public User getByLogin(String login) {
        User user = repository.getByLogin(login);
        if(user == null || user.getStatus().equals(Status.DELETED))
            return null;
        return user;
    }

    @Override
    public User update(User obj) {
        return repository.update(obj);
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll()
                .stream()
                .filter(user -> user.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
