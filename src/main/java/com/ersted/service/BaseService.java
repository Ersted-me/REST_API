package com.ersted.service;

import java.util.List;

public interface BaseService<T, R> {
    T create(T obj);

    T getById(R id);

    T update(T obj);

    boolean deleteById(R id);

    List<T> getAll();
}
