package com.myitzar.cbe.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabby on 2017/04/17.
 */
public class DCUtilImpl<T> implements DCUtil<T> {
    @Inject
    EntityManager em;

    @Override
    public void create(T object) {
        em.persist(object);
    }

    @Override
    public T readSingleByPK(Integer id, Class<T> aClass) {
        try {
            return em.find(aClass, id);
        } catch (NoResultException nre){
            return null;
        }
    }

    @Override
    public T readSingleWithFilters(String query, Map<String, Object> filters, Class<T> aClass) {
        try {
            TypedQuery<T> tQuery = em.createQuery(query, aClass);

            if (filters != null){
                for (Map.Entry<String, Object> entry : filters.entrySet()){
                    tQuery.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return tQuery.getSingleResult();
        } catch (NoResultException nre){
            return null;
        }
    }

    @Override
    public List<T> readListAll(Class<T> aClass) {
        try {
            String query = "select a from " + aClass.getName() + " a";
            return em.createQuery(query).getResultList();
        } catch (NoResultException nre) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<T> readListWithFilters(String query, Map<String, Object> filters, Class<T> aClass) {
        try {
            TypedQuery<T> tQuery = em.createQuery(query, aClass);

            if (filters != null){
                for (Map.Entry<String, Object> entry : filters.entrySet()){
                    tQuery.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return tQuery.getResultList();
        } catch (NoResultException nre){
            return Collections.emptyList();
        }
    }

    @Override
    public T update(T object) {
        return null;
    }

    @Override
    public void delete(T object) {

    }
}
