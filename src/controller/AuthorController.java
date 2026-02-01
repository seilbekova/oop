package controller;

import model.Author;
import service.interfaces.AuthorService;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;

public class AuthorController {
    private final AuthorService authorService;

    // Внедрение зависимости через конструктор (DIP)
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Создание автора
    public Author createAuthor(String name, String nationality) {
        try {
            Author author = new Author(name, nationality);
            Author savedAuthor = authorService.createAuthor(author);
            System.out.println("✅ Автор создан: " + savedAuthor.getDetails());
            return savedAuthor;
        } catch (InvalidInputException e) {
            System.out.println("❌ Ошибка создания автора: " + e.getMessage());
            return null;
        }
    }

    // Получение автора по ID
    public Author getAuthorById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Author author = authorService.getAuthorById(uuid);
            System.out.println("✅ Найден автор: " + author.getDetails());
            return author;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Неверный формат ID: " + id);
            return null;
        } catch (ResourceNotFoundException e) {
            System.out.println("❌ Автор не найден: " + e.getMessage());
            return null;
        }
    }

    // Получение всех авторов
    public List<Author> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        System.out.println("\n=== Список всех авторов (" + authors.size() + ") ===");
        for (int i = 0; i < authors.size(); i++) {
            System.out.println((i + 1) + ". " + authors.get(i).getSummary());
        }
        return authors;
    }

    // Поиск авторов по имени
    public List<Author> searchAuthors(String name) {
        List<Author> authors = authorService.searchAuthorsByName(name);
        System.out.println("\n=== Результаты поиска авторов по \"" + name + "\" ===");
        if (authors.isEmpty()) {
            System.out.println("Авторы не найдены");
        } else {
            authors.forEach(a -> System.out.println("  • " + a.getSummary()));
        }
        return authors;
    }

    // Обновление автора
    public Author updateAuthor(String id, String newName, String newNationality) {
        try {
            UUID uuid = UUID.fromString(id);
            Author existingAuthor = authorService.getAuthorById(uuid);

            if (existingAuthor == null) {
                System.out.println("❌ Автор не найден");
                return null;
            }

            existingAuthor.setName(newName);
            existingAuthor.setNationality(newNationality);

            Author updatedAuthor = authorService.updateAuthor(existingAuthor);
            System.out.println("✅ Автор обновлен: " + updatedAuthor.getDetails());
            return updatedAuthor;
        } catch (InvalidInputException e) {
            System.out.println("❌ Ошибка обновления: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Неверный формат ID");
            return null;
        }
    }

    // Удаление автора
    public boolean deleteAuthor(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            boolean deleted = authorService.deleteAuthor(uuid);
            if (deleted) {
                System.out.println("✅ Автор удален (ID: " + id + ")");
            } else {
                System.out.println("❌ Не удалось удалить автора");
            }
            return deleted;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Неверный формат ID");
            return false;
        }
    }

    // Демонстрация полиморфизма через BaseEntity
    public void demonstratePolymorphism() {
        System.out.println("\n=== Демонстрация полиморфизма ===");
        List<Author> authors = authorService.getAllAuthors();
        if (!authors.isEmpty()) {
            System.out.println("Первый автор в списке (использование BaseEntity методов):");
            Author author = authors.get(0);
            System.out.println("  - getSummary(): " + author.getSummary());
            System.out.println("  - getDetails(): " + author.getDetails());
            System.out.println("  - printBasicInfo(): ");
            author.printBasicInfo();
        }
    }
} // ← ЗАКРЫВАЮЩАЯ СКОБКА ДЛЯ КЛАССА AuthorController