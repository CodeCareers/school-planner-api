package com.codecareers.schoolplannerapi.datalayer;

public interface SimpleTextDB<T> {

    int save(T t);

    boolean update(T t);
    
    boolean delete(int id);

    T findByID(int id);
    
}
