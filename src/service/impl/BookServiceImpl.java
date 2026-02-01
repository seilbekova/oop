package service.impl;

import model.Book;
import model.Author;
import repository.interfaces.BookRepository;
import repository.interfaces.AuthorRepository;
import service.interfaces.BookService;
import exception.InvalidInputException;
import exception.DuplicateResourceException;
import exception.ResourceNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Comparator;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book createBook(Book book) throws InvalidInputException, ResourceNotFoundException {
        // 1. Валидация книги
        book.validate();

        // 2. Проверка существования автора
        if (!authorRepository.exists(book.getAuthor().getId())) {
            throw new ResourceNotFoundException("Author not found with id: " + book.getAuthor().getId());
        }

        // 3. Проверка уникальности ISBN
        if (bookRepository.isbnExists(book.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN " + book.getIsbn() + " already exists");
        }

        // 4. Бизнес-правила
        if (book.getPrice().compareTo(new BigDecimal("10000")) > 0) {
            throw new InvalidInputException("Book price cannot exceed $10,000");
        }

        if (book.getYear() > java.time.LocalDate.now().getYear()) {
            throw new InvalidInputException("Book year cannot be in the future");
        }

        // 5. Сохранение
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(UUID id) throws ResourceNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Book book) throws InvalidInputException, ResourceNotFoundException {
        // Проверяем существование книги
        if (!bookRepository.exists(book.getId())) {
            throw new ResourceNotFoundException("Cannot update non-existing book");
        }

        // Проверяем существование автора
        if (!authorRepository.exists(book.getAuthor().getId())) {
            throw new ResourceNotFoundException("Author not found with id: " + book.getAuthor().getId());
        }

        // Валидация
        book.validate();

        // Обновление
        return bookRepository.update(book);
    }

    @Override
    public boolean deleteBook(UUID id) throws ResourceNotFoundException {
        if (!bookRepository.exists(id)) {
            throw new ResourceNotFoundException("Cannot delete non-existing book");
        }
        return bookRepository.delete(id);
    }

    @Override
    public List<Book> getBooksByAuthor(UUID authorId) throws ResourceNotFoundException {
        // Проверяем существование автора
        if (!authorRepository.exists(authorId)) {
            throw new ResourceNotFoundException("Author not found with id: " + authorId);
        }
        return bookRepository.findByAuthor(authorId);
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return getAllBooks().stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        // Используем интерфейс Searchable (полиморфизм)
        return getAllBooks().stream()
                .filter(book -> book.matches(keyword))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksInPriceRange(BigDecimal min, BigDecimal max) {
        if (min.compareTo(max) > 0) {
            throw new InvalidInputException("Minimum price cannot be greater than maximum price");
        }

        return getAllBooks().stream()
                .filter(book -> {
                    BigDecimal price = book.getPrice();
                    return price.compareTo(min) >= 0 && price.compareTo(max) <= 0;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean bookExists(UUID id) {
        return bookRepository.exists(id);
    }

    @Override
    public boolean isbnExists(String isbn) {
        return bookRepository.isbnExists(isbn);
    }

    @Override
    public List<Book> getBooksSortedByPrice(boolean ascending) {
        List<Book> books = getAllBooks();

        // Лямбда-выражение для сортировки
        books.sort((book1, book2) -> {
            int comparison = book1.getPrice().compareTo(book2.getPrice());
            return ascending ? comparison : -comparison;
        });

        return books;
    }

    @Override
    public List<Book> getBooksSortedByYear(boolean ascending) {
        List<Book> books = getAllBooks();

        // Лямбда-выражение для сортировки
        books.sort((book1, book2) -> {
            int comparison = Integer.compare(book1.getYear(), book2.getYear());
            return ascending ? comparison : -comparison;
        });

        return books;
    }
}