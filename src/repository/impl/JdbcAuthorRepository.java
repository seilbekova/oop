package repository.impl;

import model.Author;
import repository.interfaces.AuthorRepository;
import java.util.*;

// Простейшая версия для начала (без БД)
public class JdbcAuthorRepository implements AuthorRepository {
    private final Map<UUID, Author> authors = new HashMap<>();

    @Override
    public Author save(Author author) {
        authors.put(author.getId(), author);
        return author;
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return Optional.ofNullable(authors.get(id));
    }

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(authors.values());
    }

    @Override
    public Author update(Author author) {
        if (authors.containsKey(author.getId())) {
            authors.put(author.getId(), author);
            return author;
        }
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        return authors.remove(id) != null;
    }

    @Override
    public boolean exists(UUID id) {
        return authors.containsKey(id);
    }

    @Override
    public List<Author> findByName(String name) {
        List<Author> result = new ArrayList<>();
        for (Author author : authors.values()) {
            if (author.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(author);
            }
        }
        return result;
    }
}