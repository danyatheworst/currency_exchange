package main.java.com.danyatheworst.currency;

import java.util.List;

public interface CrudRepository<T> {
    List<T> findAll();
    int create(T currency);
}
