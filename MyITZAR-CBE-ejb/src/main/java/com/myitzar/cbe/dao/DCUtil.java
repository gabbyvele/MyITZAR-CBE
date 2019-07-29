package com.myitzar.cbe.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Gabby on 2017/04/16.
 */
public interface DCUtil<T> {
    void create(T object);

    T readSingleByPK(Integer id, Class<T> aClass);

    T readSingleWithFilters(String query, Map<String, Object> filters, Class<T> aClass);

    List<T> readListAll(Class<T> aClass);

    List<T> readListWithFilters(String query, Map<String, Object> filters, Class<T> aClass);

    T update(T object);

    void delete(T object);
}