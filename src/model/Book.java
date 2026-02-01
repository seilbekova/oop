package model;

import java.math.BigDecimal;
import exception.InvalidInputException;  // ДОБАВИТЬ этот импорт

public class Book extends BaseEntity implements Validatable, Searchable, PricedItem {
    private Author author;
    private String isbn;
    private BigDecimal price;
    private int year;
    private String category;

    public Book(String name, Author author, String isbn,
                BigDecimal price, int year, String category) {
        super(name);
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.year = year;
        this.category = category;
    }

    @Override
    public void validate() throws InvalidInputException {  // ИЗМЕНИТЬ тип исключения
        if (getName() == null || getName().trim().isEmpty()) {
            throw new InvalidInputException("Book title cannot be empty");
        }
        if (author == null) {
            throw new InvalidInputException("Book must have an author");
        }
        // Проверка автора
        try {
            author.validate();
        } catch (InvalidInputException e) {  // ИЗМЕНИТЬ тип исключения
            throw new InvalidInputException("Author validation failed: " + e.getMessage());
        }
        if (isbn == null || !isbn.matches("\\d{13}")) {
            throw new InvalidInputException("ISBN must be 13 digits");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidInputException("Price must be non-negative");
        }
        if (year < 1000 || year > 2026) {
            throw new InvalidInputException("Invalid year (must be between 1000 and 2026)");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new InvalidInputException("Category cannot be empty");
        }
    }

    @Override
    public String getDetails() {
        return String.format("Book: %s by %s (ISBN: %s, Year: %d, Category: %s, Price: $%s)",
                getName(), author.getName(), isbn, year, category, price.toString());
    }

    // Геттеры и сеттеры остаются без изменений
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthorInfo() {
        return "Author: " + author.getName() + " (" + author.getNationality() + ")";
    }

    @Override
    public String toString() {
        return String.format("Book[id=%s, title='%s', author='%s', category='%s']",
                getId().toString().substring(0, 8), getName(), author.getName(), category);
    }

    @Override
    public boolean matches(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return false;
        }
        String lowerKeyword = keyword.toLowerCase();
        return getName().toLowerCase().contains(lowerKeyword) ||
                getAuthor().getName().toLowerCase().contains(lowerKeyword) ||
                getIsbn().contains(keyword) ||
                getCategory().toLowerCase().contains(lowerKeyword);
    }
}