package repository.interfaces;

import model.Book;
import java.util.List;
import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, UUID> {
    List<Book> findByAuthor(UUID authorId);
    boolean isbnExists(String isbn);
}
