package repository.impl;

import model.Book;
import repository.interfaces.BookRepository;
import java.util.*;

// Простейшая версия для начала (без БД)
public class JdbcBookRepository implements BookRepository {
    private final Map<UUID, Book> books = new HashMap<>();

    @Override
    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Book update(Book book) {
        if (books.containsKey(book.getId())) {
            books.put(book.getId(), book);
            return book;
        }
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        return books.remove(id) != null;
    }

    @Override
    public boolean exists(UUID id) {
        return books.containsKey(id);
    }

    @Override
    public List<Book> findByAuthor(UUID authorId) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().getId().equals(authorId)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public boolean isbnExists(String isbn) {
        for (Book book : books.values()) {
            if (book.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }
}