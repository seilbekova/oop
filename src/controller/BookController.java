package controller;

import model.Book;
import model.Author;
import service.interfaces.BookService;
import service.interfaces.AuthorService;
import exception.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // Создание книги
    public Book createBook(String title, String authorId, String isbn,
                           String priceStr, String yearStr, String category) {
        try {
            // Конвертация параметров
            UUID authorUUID = UUID.fromString(authorId);
            BigDecimal price = new BigDecimal(priceStr);
            int year = Integer.parseInt(yearStr);

            // Проверка существования автора
            Author author = authorService.getAuthorById(authorUUID);
            if (author == null) {
                System.out.println("❌ Автор не найден (ID: " + authorId + ")");
                return null;
            }

            // Создание и сохранение книги
            Book book = new Book(title, author, isbn, price, year, category);
            Book savedBook = bookService.createBook(book);
            System.out.println("✅ Книга создана: " + savedBook.getDetails());
            return savedBook;

        } catch (NumberFormatException e) {
            System.out.println("❌ Неверный формат числа: " + e.getMessage());
            return null;
        } catch (InvalidInputException e) {
            System.out.println("❌ Ошибка валидации: " + e.getMessage());
            return null;
        } catch (DuplicateResourceException e) {
            System.out.println("❌ Дубликат: " + e.getMessage());
            return null;
        } catch (ResourceNotFoundException e) {
            System.out.println("❌ Ресурс не найден: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Неверный формат UUID: " + authorId);
            return null;
        }
    }

    // Получение книги по ID
    public Book getBookById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Book book = bookService.getBookById(uuid);
            System.out.println("✅ Найдена книга: " + book.getDetails());
            return book;
        } catch (ResourceNotFoundException e) {
            System.out.println("❌ Книга не найдена: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Неверный формат ID");
            return null;
        }
    }

    // Получение всех книг
    public List<Book> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        System.out.println("\n=== Список всех книг (" + books.size() + ") ===");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println((i + 1) + ". " + book.getSummary() +
                    " | Категория: " + book.getCategory() +
                    " | Цена: $" + book.getPrice());
        }
        return books;
    }

    // Поиск книг
    public List<Book> searchBooks(String keyword) {
        List<Book> books = bookService.searchBooks(keyword);
        System.out.println("\n=== Результаты поиска по \"" + keyword + "\" ===");
        if (books.isEmpty()) {
            System.out.println("Книги не найдены");
        } else {
            books.forEach(b -> System.out.println("  • " + b.getSummary()));
        }
        return books;
    }

    // Сортировка книг по цене (лямбда-выражение)
    public List<Book> sortBooksByPrice(boolean ascending) {
        List<Book> books = bookService.getBooksSortedByPrice(ascending);
        String order = ascending ? "возрастанию" : "убыванию";
        System.out.println("\n=== Книги, отсортированные по цене (" + order + ") ===");
        books.forEach(b -> System.out.println("  • " + b.getName() + " - $" + b.getPrice()));
        return books;
    }

    // Сортировка книг по году (лямбда-выражение)
    public List<Book> sortBooksByYear(boolean ascending) {
        List<Book> books = bookService.getBooksSortedByYear(ascending);
        String order = ascending ? "возрастанию" : "убыванию";
        System.out.println("\n=== Книги, отсортированные по году (" + order + ") ===");
        books.forEach(b -> System.out.println("  • " + b.getName() + " - " + b.getYear()));
        return books;
    }

    // Книги по категории
    public List<Book> getBooksByCategory(String category) {
        List<Book> books = bookService.getBooksByCategory(category);
        System.out.println("\n=== Книги в категории \"" + category + "\" ===");
        if (books.isEmpty()) {
            System.out.println("Книги не найдены");
        } else {
            books.forEach(b -> System.out.println("  • " + b.getSummary()));
        }
        return books;
    }

    // Книги по автору
    public List<Book> getBooksByAuthor(String authorId) {
        try {
            UUID uuid = UUID.fromString(authorId);
            List<Book> books = bookService.getBooksByAuthor(uuid);
            System.out.println("\n=== Книги автора (ID: " + authorId + ") ===");
            if (books.isEmpty()) {
                System.out.println("Книги не найдены");
            } else {
                books.forEach(b -> System.out.println("  • " + b.getSummary()));
            }
            return books;
        } catch (ResourceNotFoundException e) {
            System.out.println("❌ Автор не найден: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Неверный формат ID");
            return null;
        }
    }

    // Демонстрация интерфейсов и композиции
    public void demonstrateAdvancedFeatures(Book book) {
        if (book == null) {
            System.out.println("❌ Книга не передана для демонстрации");
            return;
        }

        System.out.println("\n=== Демонстрация продвинутых возможностей ===");

        // 1. Интерфейс Searchable
        System.out.println("1. Интерфейс Searchable:");
        System.out.println("   - matches('1984'): " + book.matches("1984"));
        System.out.println("   - matchesIgnoreCase('DYSTOPIAN'): " +
                book.matchesIgnoreCase("DYSTOPIAN"));

        // 2. Интерфейс PricedItem
        System.out.println("2. Интерфейс PricedItem:");
        System.out.println("   - getPrice(): $" + book.getPrice());
        System.out.println("   - getPriceWithTax(0.1): $" +
                book.getPriceWithTax(new BigDecimal("0.1")));
        System.out.println("   - Статический метод getCurrency(): " +
                model.PricedItem.getCurrency());

        // 3. Композиция (Book has Author)
        System.out.println("3. Композиция (Book → Author):");
        System.out.println("   - Книга: " + book.getName());
        System.out.println("   - Автор: " + book.getAuthor().getName());
        System.out.println("   - Национальность автора: " +
                book.getAuthor().getNationality());

        // 4. Полиморфизм через BaseEntity
        System.out.println("4. Полиморфизм через BaseEntity:");
        System.out.println("   - getSummary(): " + book.getSummary());
        System.out.println("   - getDetails(): " + book.getDetails());
    }

    // Обновление книги
    public Book updateBook(String id, String title, String authorId, String isbn,
                           String priceStr, String yearStr, String category) {
        try {
            UUID uuid = UUID.fromString(id);
            UUID authorUUID = UUID.fromString(authorId);
            BigDecimal price = new BigDecimal(priceStr);
            int year = Integer.parseInt(yearStr);

            // Проверка существования автора
            Author author = authorService.getAuthorById(authorUUID);
            if (author == null) {
                System.out.println("❌ Автор не найден");
                return null;
            }

            // Создание обновленной книги
            Book book = new Book(title, author, isbn, price, year, category);
            book.setId(uuid);

            Book updatedBook = bookService.updateBook(book);
            System.out.println("✅ Книга обновлена: " + updatedBook.getDetails());
            return updatedBook;

        } catch (Exception e) {
            System.out.println("❌ Ошибка обновления: " + e.getMessage());
            return null;
        }
    }

    // Удаление книги
    public boolean deleteBook(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            boolean deleted = bookService.deleteBook(uuid);
            if (deleted) {
                System.out.println("✅ Книга удалена (ID: " + id + ")");
            } else {
                System.out.println("❌ Не удалось удалить книгу");
            }
            return deleted;
        } catch (Exception e) {
            System.out.println("❌ Ошибка удаления: " + e.getMessage());
            return false;
        }
    }
}