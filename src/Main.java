import model.Author;
import model.Book;
import model.BaseEntity;
import dao.AuthorDAO;
import dao.BookDAO;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== LIBRARY MANAGEMENT SYSTEM ===\n");

        try {

            AuthorDAO authorDAO = new AuthorDAO();
            BookDAO bookDAO = new BookDAO();


            System.out.println("1. CREATING AUTHORS:");
            System.out.println("-".repeat(40));

            Author author1 = new Author(0, "J.K. Rowling", "British");
            Author author2 = new Author(0, "George Orwell", "English");
            Author author3 = new Author(0, "J.R.R. Tolkien", "English");


            try {
                author1.validate();
                author2.validate();
                author3.validate();
                System.out.println("✓ All authors validated successfully");
            } catch (Exception e) {
                System.out.println("Validation error: " + e.getMessage());
            }


            if (authorDAO.addAuthor(author1)) {
                System.out.println("✓ Author created: " + author1.getName() + " (ID: " + author1.getId() + ")");
            }
            if (authorDAO.addAuthor(author2)) {
                System.out.println("✓ Author created: " + author2.getName() + " (ID: " + author2.getId() + ")");
            }
            if (authorDAO.addAuthor(author3)) {
                System.out.println("✓ Author created: " + author3.getName() + " (ID: " + author3.getId() + ")");
            }


            System.out.println("\n2. CREATING BOOKS:");
            System.out.println("-".repeat(40));

            Book book1 = new Book(0, "Harry Potter and the Philosopher's Stone",
                    author1, "9780747532743", 19.99, 1997);
            Book book2 = new Book(0, "1984", author2, "9780451524935", 14.99, 1949);
            Book book3 = new Book(0, "The Hobbit", author3, "9780547928227", 22.50, 1937);


            try {
                book1.validate();
                book2.validate();
                book3.validate();
                System.out.println("✓ All books validated successfully");
            } catch (Exception e) {
                System.out.println("Validation error: " + e.getMessage());
            }


            if (!bookDAO.isbnExists(book1.getIsbn())) {
                if (bookDAO.addBook(book1)) {
                    System.out.println("✓ Book created: " + book1.getName());
                }
            } else {
                System.out.println("✗ Book with ISBN " + book1.getIsbn() + " already exists");
            }

            if (!bookDAO.isbnExists(book2.getIsbn())) {
                if (bookDAO.addBook(book2)) {
                    System.out.println("✓ Book created: " + book2.getName());
                }
            } else {
                System.out.println("✗ Book with ISBN " + book2.getIsbn() + " already exists");
            }

            if (!bookDAO.isbnExists(book3.getIsbn())) {
                if (bookDAO.addBook(book3)) {
                    System.out.println("✓ Book created: " + book3.getName());
                }
            } else {
                System.out.println("✗ Book with ISBN " + book3.getIsbn() + " already exists");
            }


            System.out.println("\n3. READING ALL RECORDS:");
            System.out.println("-".repeat(40));

            System.out.println("\n--- ALL AUTHORS ---");
            List<Author> authors = authorDAO.getAllAuthors();
            for (Author author : authors) {
                author.printBasicInfo();
            }

            System.out.println("\n--- ALL BOOKS ---");
            List<Book> books = bookDAO.getAllBooks();
            for (Book book : books) {
                book.printBasicInfo();
                System.out.println();
            }


            System.out.println("4. DEMONSTRATING POLYMORPHISM:");
            System.out.println("-".repeat(40));

            BaseEntity[] entities = {
                    author1, book1, author2, book2, author3, book3
            };

            System.out.println("\nCalling getDetails() method polymorphically:");
            for (BaseEntity entity : entities) {
                System.out.println("  • " + entity.getDetails());
            }

            System.out.println("\nCalling printBasicInfo() method polymorphically:");
            for (BaseEntity entity : entities) {
                entity.printBasicInfo();
            }


            System.out.println("\n5. UPDATING RECORDS:");
            System.out.println("-".repeat(40));


            Book bookToUpdate = bookDAO.getBookById(book1.getId());
            if (bookToUpdate != null) {
                System.out.println("Current price of '" + bookToUpdate.getName() + "': $" + bookToUpdate.getPrice());
                bookToUpdate.setPrice(24.99);

                if (bookDAO.updateBook(bookToUpdate)) {
                    System.out.println("✓ Book price updated to: $" + bookToUpdate.getPrice());
                }
            }


            Author authorToUpdate = authorDAO.getAuthorById(author1.getId());
            if (authorToUpdate != null) {
                System.out.println("\nCurrent nationality of '" + authorToUpdate.getName() + "': " + authorToUpdate.getNationality());
                authorToUpdate.setNationality("British (Scottish)");

                if (authorDAO.updateAuthor(authorToUpdate)) {
                    System.out.println("✓ Author nationality updated to: " + authorToUpdate.getNationality());
                }
            }


            System.out.println("\n6. DELETING A RECORD:");
            System.out.println("-".repeat(40));


            Book testBook = new Book(0, "Test Book", author1, "9999999999", 9.99, 2023);
            if (bookDAO.addBook(testBook)) {
                System.out.println("Test book created for deletion demo");


                if (bookDAO.deleteBook(testBook.getId())) {
                    System.out.println("✓ Test book deleted successfully");
                }
            }


            System.out.println("\n7. DEMONSTRATING VALIDATION ERRORS:");
            System.out.println("-".repeat(40));

            try {

                Book invalidBook1 = new Book(0, "", author1, "1111111111", 10.00, 2023);
                invalidBook1.validate();
            } catch (Exception e) {
                System.out.println("✓ Caught validation error 1: " + e.getMessage());
            }

            try {

                Book invalidBook2 = new Book(0, "Invalid Book", author1, "2222222222", -5.00, 2023);
                invalidBook2.validate();
            } catch (Exception e) {
                System.out.println("✓ Caught validation error 2: " + e.getMessage());
            }

            try {

                Book invalidBook3 = new Book(0, "Another Invalid", author1, "3333333333", 15.00, 500);
                invalidBook3.validate();
            } catch (Exception e) {
                System.out.println("✓ Caught validation error 3: " + e.getMessage());
            }

            try {

                Author invalidAuthor = new Author(0, "", "Unknown");
                invalidAuthor.validate();
            } catch (Exception e) {
                System.out.println("✓ Caught validation error 4: " + e.getMessage());
            }


            System.out.println("\n8. FINAL STATUS:");
            System.out.println("-".repeat(40));

            System.out.println("Total Authors in database: " + authorDAO.getAllAuthors().size());
            System.out.println("Total Books in database: " + bookDAO.getAllBooks().size());


            System.out.println("\nBooks by J.R.R. Tolkien:");
            List<Book> tolkienBooks = bookDAO.getBooksByAuthor(author3.getId());
            for (Book book : tolkienBooks) {
                System.out.println("  • " + book.getName() + " ($" + book.getPrice() + ")");
            }

            System.out.println("\n" + "=".repeat(50));
            System.out.println("PROGRAM COMPLETED SUCCESSFULLY!");
            System.out.println("=".repeat(50));

        } catch (Exception e) {
            System.err.println("\n❌ ERROR IN PROGRAM: " + e.getMessage());
            e.printStackTrace();
        }
    }
}