package com.ersted.controller;

import com.ersted.model.User;

public interface UserController extends BaseController<User, Long>{
    User getByLogin(String login);
}
