package dao;

import db.DatabaseConnection;
import model.Book;
import model.Author;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {


    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author_id, isbn, price, year) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, book.getName());
            pstmt.setInt(2, book.getAuthor().getId());
            pstmt.setString(3, book.getIsbn());
            pstmt.setDouble(4, book.getPrice());
            pstmt.setInt(5, book.getYear());
            pstmt.executeUpdate();


            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                book.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }

        return false;
    }


    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, a.name as author_name, a.nationality " +
                "FROM books b JOIN authors a ON b.author_id = a.id " +
                "ORDER BY b.title";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Author author = new Author(
                        rs.getInt("author_id"),
                        rs.getString("author_name"),
                        rs.getString("nationality")
                );


                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        author,
                        rs.getString("isbn"),
                        rs.getDouble("price"),
                        rs.getInt("year")
                );

                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error getting books: " + e.getMessage());
        }

        return books;
    }


    public Book getBookById(int id) {
        String sql = "SELECT b.*, a.name as author_name, a.nationality " +
                "FROM books b JOIN authors a ON b.author_id = a.id " +
                "WHERE b.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Author author = new Author(
                        rs.getInt("author_id"),
                        rs.getString("author_name"),
                        rs.getString("nationality")
                );

                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        author,
                        rs.getString("isbn"),
                        rs.getDouble("price"),
                        rs.getInt("year")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting book by ID: " + e.getMessage());
        }

        return null;
    }


    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author_id = ?, isbn = ?, price = ?, year = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getName());
            pstmt.setInt(2, book.getAuthor().getId());
            pstmt.setString(3, book.getIsbn());
            pstmt.setDouble(4, book.getPrice());
            pstmt.setInt(5, book.getYear());
            pstmt.setInt(6, book.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
        }

        return false;
    }


    public boolean deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }

        return false;
    }


    public boolean isbnExists(String isbn) {
        String sql = "SELECT 1 FROM books WHERE isbn = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking ISBN: " + e.getMessage());
        }

        return false;
    }


    public List<Book> getBooksByAuthor(int authorId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, a.name as author_name, a.nationality " +
                "FROM books b JOIN authors a ON b.author_id = a.id " +
                "WHERE b.author_id = ? ORDER BY b.title";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, authorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Author author = new Author(
                        rs.getInt("author_id"),
                        rs.getString("author_name"),
                        rs.getString("nationality")
                );

                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        author,
                        rs.getString("isbn"),
                        rs.getDouble("price"),
                        rs.getInt("year")
                );

                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error getting books by author: " + e.getMessage());
        }

        return books;
    }
}
