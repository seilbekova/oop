package service.impl;

import model.Author;
import repository.interfaces.AuthorRepository;
import service.interfaces.AuthorService;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // МЕТОД ДОЛЖЕН БЫТЬ В ТОЧНОСТИ КАК В ИНТЕРФЕЙСЕ
    @Override
    public Author createAuthor(Author author) throws InvalidInputException {
        // 1. Валидация
        author.validate();

        // 2. Проверка на дубликаты (опционально)
        // List<Author> existing = authorRepository.findByName(author.getName());
        // if (!existing.isEmpty()) {
        //     throw new InvalidInputException("Author already exists");
        // }

        // 3. Сохранение
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthorById(UUID id) throws ResourceNotFoundException {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author updateAuthor(Author author) throws InvalidInputException {
        // Проверяем существование
        if (!authorRepository.exists(author.getId())) {
            throw new InvalidInputException("Cannot update non-existing author");
        }

        // Валидация
        author.validate();

        // Обновление
        return authorRepository.update(author);
    }

    @Override
    public boolean deleteAuthor(UUID id) {
        // Просто вызываем репозиторий
        return authorRepository.delete(id);
    }

    @Override
    public List<Author> searchAuthorsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllAuthors();
        }

        return authorRepository.findAll().stream()
                .filter(author -> author.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Author> getAuthorsByNationality(String nationality) {
        return authorRepository.findAll().stream()
                .filter(author -> author.getNationality().equalsIgnoreCase(nationality))
                .collect(Collectors.toList());
    }

    @Override
    public boolean authorExists(UUID id) {
        return authorRepository.exists(id);
    }
}