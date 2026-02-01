package repository.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudRepository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    T update(T entity);
    boolean delete(ID id);
    boolean exists(ID id);
}