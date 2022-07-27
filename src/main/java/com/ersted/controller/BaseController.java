package com.ersted.controller;

import java.util.List;

public interface BaseController<T,R>{
    T create(T obj);

    T getById(R id);

    T update(T obj);

    boolean deleteById(R id);

    List<T> getAll();
}
