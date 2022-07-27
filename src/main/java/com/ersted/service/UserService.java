package com.ersted.service;

import com.ersted.model.User;

public interface UserService extends BaseService<User,Long>{
    User getByLogin(String login);
}
