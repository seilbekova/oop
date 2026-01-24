package dao;

import db.DatabaseConnection;
import model.Author;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {


    public boolean addAuthor(Author author) {
        String sql = "INSERT INTO authors (name, nationality) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, author.getName());
            pstmt.setString(2, author.getNationality());
            pstmt.executeUpdate();


            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                author.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding author: " + e.getMessage());
        }
        return false;
    }


    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Author author = new Author(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nationality")
                );
                authors.add(author);
            }
        } catch (SQLException e) {
            System.err.println("Error getting authors: " + e.getMessage());
        }

        return authors;
    }


    public Author getAuthorById(int id) {
        String sql = "SELECT * FROM authors WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Author(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nationality")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting author by ID: " + e.getMessage());
        }

        return null;
    }


    public boolean updateAuthor(Author author) {
        String sql = "UPDATE authors SET name = ?, nationality = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, author.getName());
            pstmt.setString(2, author.getNationality());
            pstmt.setInt(3, author.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating author: " + e.getMessage());
        }

        return false;
    }


    public boolean deleteAuthor(int id) {
        String sql = "DELETE FROM authors WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting author: " + e.getMessage());
        }

        return false;
    }


    public boolean authorExists(int id) {
        String sql = "SELECT 1 FROM authors WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking author existence: " + e.getMessage());
        }

        return false;
    }
}