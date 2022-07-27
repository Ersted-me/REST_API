package com.ersted.repository;

import com.ersted.model.User;

public interface UserRepository extends BaseRepository<User, Long>{
    User getByLogin(String login);
}
