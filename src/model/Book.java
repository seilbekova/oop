package model;


public class Book extends BaseEntity {
    private Author author;
    private String isbn;
    private double price;
    private int year;

    public Book(int id, String title, Author author, String isbn, double price, int year) {
        super(id, title);
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.year = year;
    }

    @Override
    public void validate() throws Exception {

        if (getName() == null || getName().trim().isEmpty()) {
            throw new Exception("Book title cannot be empty");
        }

        if (author == null) {
            throw new Exception("Book must have an author");
        }

        if (price <= 0) {
            throw new Exception("Price must be positive");
        }

        if (year < 1000 || year > 2025) {
            throw new Exception("Invalid publication year");
        }
    }

    @Override
    public String getDetails() {
        return "Book: " + getName() + " by " + author.getName() +
                " (" + year + ") - $" + price + " [ISBN: " + isbn + "]";
    }


    @Override
    public void printBasicInfo() {
        super.printBasicInfo();
        System.out.println("Author: " + author.getName());
        System.out.println("ISBN: " + isbn + ", Year: " + year + ", Price: $" + price);
    }


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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1000 || year > 2025) {
            throw new IllegalArgumentException("Invalid year");
        }
        this.year = year;
    }
}
