package repository.interfaces;

import model.Author;
import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends CrudRepository<Author, UUID> {
    List<Author> findByName(String name);
}