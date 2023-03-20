package com.codecareers.schoolplannerapi.datalayer;

import java.util.List;

public interface SimpleTextDB<T> {

    int save(T t);

    boolean update(T t);
    
    boolean delete(int id);

    T findByID(int id);

    List<T> findByClass(String classCategory);

    List<T> findByCurrentlyDue();
    
}
