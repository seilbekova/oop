package service.interfaces;

import model.Book;
import exception.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BookService {
    Book createBook(Book book) throws InvalidInputException, ResourceNotFoundException;
    Book getBookById(UUID id) throws ResourceNotFoundException;  // Добавить throws
    List<Book> getAllBooks();
    Book updateBook(Book book) throws InvalidInputException, ResourceNotFoundException;
    boolean deleteBook(UUID id) throws ResourceNotFoundException;
    List<Book> getBooksByAuthor(UUID authorId) throws ResourceNotFoundException;  // Добавить throws
    List<Book> getBooksByCategory(String category);
    List<Book> searchBooks(String keyword);
    List<Book> getBooksInPriceRange(BigDecimal min, BigDecimal max);
    boolean bookExists(UUID id);
    boolean isbnExists(String isbn);
    List<Book> getBooksSortedByPrice(boolean ascending);
    List<Book> getBooksSortedByYear(boolean ascending);
}