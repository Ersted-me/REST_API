package com.ersted.controller.impl;

import com.ersted.controller.UserController;
import com.ersted.model.User;
import com.ersted.service.UserService;

import java.util.List;

public class UserControllerImpl implements UserController {
    private final UserService userService;

    public UserControllerImpl(UserService service) {
        this.userService = service;
    }

    @Override
    public User create(User obj) {
        return userService.create(obj);
    }

    @Override
    public User getById(Long id) {
        return userService.getById(id);
    }

    @Override
    public User getByLogin(String login) {
        return userService.getByLogin(login);
    }

    @Override
    public User update(User obj) {
        return userService.update(obj);
    }

    @Override
    public boolean deleteById(Long id) {
        return userService.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userService.getAll();
    }
}
