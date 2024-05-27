package main.java.com.danyatheworst.currency;

import java.util.List;

public interface CrudRepository<T> {
    List<T> findAll();
    T save(T currency);
}
