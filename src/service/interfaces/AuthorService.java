package service.interfaces;

import model.Author;
import exception.InvalidInputException;
import java.util.List;
import java.util.UUID;

public interface AuthorService {
    Author createAuthor(Author author) throws InvalidInputException;
    Author getAuthorById(UUID id);  // УБРАТЬ throws ResourceNotFoundException
    List<Author> getAllAuthors();
    Author updateAuthor(Author author) throws InvalidInputException;
    boolean deleteAuthor(UUID id);
    List<Author> searchAuthorsByName(String name);
    List<Author> getAuthorsByNationality(String nationality);
    boolean authorExists(UUID id);
}