import model.*;
import repository.impl.*;
import repository.interfaces.*;
import service.impl.*;
import service.interfaces.*;
import controller.*;
import utils.*;
import db.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Assignment 4: SOLID & Advanced OOP ===\n");

        try {
            // 1. Инициализация
            Connection connection = DatabaseConnection.getConnection();

            // 2. Repository слой
            AuthorRepository authorRepo = new JdbcAuthorRepository(connection);
            BookRepository bookRepo = new JdbcBookRepository(connection);

            // 3. Service слой
            AuthorService authorService = new AuthorServiceImpl(authorRepo);
            BookService bookService = new BookServiceImpl(bookRepo, authorRepo);

            // 4. Controller слой
            AuthorController authorController = new AuthorController(authorService);
            BookController bookController = new BookController(bookService, authorService);

            // 5. Демонстрация SOLID
            System.out.println("=== 1. SOLID Архитектура ===");
            authorController.createAuthor("George Orwell", "British");
            authorController.getAllAuthors();

            // 6. Демонстрация Advanced OOP
            System.out.println("\n=== 2. Advanced OOP ===");
            Author author = authorController.getAuthorById("test-id"); // null для демонстрации
            if (author != null) {
                System.out.println("Автор: " + author.getSummary());
            }

            // 7. Демонстрация лямбд
            System.out.println("\n=== 3. Лямбда-выражения ===");
            List<Book> books = bookService.getAllBooks();
            SortingUtils.demonstrateLambdas(books);

            // 8. Демонстрация рефлексии
            System.out.println("\n=== 4. Рефлексия ===");
            ReflectionUtils.demonstrateReflection();
            if (!books.isEmpty()) {
                ReflectionUtils.inspectClass(books.get(0));
            }

            // 9. Демонстрация исключений
            System.out.println("\n=== 5. Обработка исключений ===");
            authorController.createAuthor("", ""); // Должно вызвать ошибку

            System.out.println("\n✅ Проект успешно скомпилирован и запущен!");

        } catch (Exception e) {
            System.out.println("❌ Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}