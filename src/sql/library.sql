DROP DATABASE IF EXISTS library_db;
CREATE DATABASE library_db;
USE library_db;

CREATE TABLE authors (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         nationality VARCHAR(50),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         INDEX idx_author_name (name)
);

CREATE TABLE books (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(200) NOT NULL,
                       author_id INT NOT NULL,
                       isbn VARCHAR(20) NOT NULL,
                       price DECIMAL(10, 2) NOT NULL,
                       year INT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (author_id)
                           REFERENCES authors(id)
                           ON DELETE CASCADE
                           ON UPDATE CASCADE,
                       UNIQUE KEY uk_isbn (isbn),
                       CONSTRAINT chk_price_positive CHECK (price > 0),
                       CONSTRAINT chk_year_reasonable CHECK (year >= 1000 AND year <= YEAR(CURRENT_DATE) + 1),
    INDEX idx_book_title (title),
    INDEX idx_book_author (author_id),
    INDEX idx_book_isbn (isbn),
    INDEX idx_book_year (year)
);

INSERT INTO authors (name, nationality) VALUES
                                            ('J.K. Rowling', 'British'),
                                            ('George Orwell', 'English'),
                                            ('J.R.R. Tolkien', 'English'),
                                            ('Agatha Christie', 'English'),
                                            ('Stephen King', 'American'),
                                            ('Leo Tolstoy', 'Russian'),
                                            ('F. Scott Fitzgerald', 'American'),
                                            ('Harper Lee', 'American'),
                                            ('Mark Twain', 'American'),
                                            ('Jane Austen', 'English');

INSERT INTO books (title, author_id, isbn, price, year) VALUES
                                                            ('Harry Potter and the Philosopher''s Stone', 1, '9780747532743', 19.99, 1997),
                                                            ('Harry Potter and the Chamber of Secrets', 1, '9780747538493', 21.50, 1998),
                                                            ('Harry Potter and the Prisoner of Azkaban', 1, '9780747542155', 22.99, 1999),
                                                            ('Harry Potter and the Goblet of Fire', 1, '9780747546245', 24.99, 2000),
                                                            ('1984', 2, '9780451524935', 14.99, 1949),
                                                            ('Animal Farm', 2, '9780451526342', 12.99, 1945),
                                                            ('Homage to Catalonia', 2, '9780156421171', 16.50, 1938),
                                                            ('The Hobbit', 3, '9780547928227', 22.50, 1937),
                                                            ('The Lord of the Rings: The Fellowship of the Ring', 3, '9780544003415', 25.99, 1954),
                                                            ('The Lord of the Rings: The Two Towers', 3, '9780544003416', 25.99, 1954),
                                                            ('The Lord of the Rings: The Return of the King', 3, '9780544003417', 25.99, 1955),
                                                            ('The Silmarillion', 3, '9780544338012', 28.99, 1977),
                                                            ('Murder on the Orient Express', 4, '9780062693662', 15.99, 1934),
                                                            ('Death on the Nile', 4, '9780062073551', 16.50, 1937),
                                                            ('And Then There Were None', 4, '9780062073488', 14.99, 1939),
                                                            ('The Shining', 5, '9780385121675', 18.99, 1977),
                                                            ('It', 5, '9780450411434', 22.99, 1986),
                                                            ('The Stand', 5, '9780385121682', 24.99, 1978),
                                                            ('Carrie', 5, '9780385086950', 16.99, 1974),
                                                            ('War and Peace', 6, '9781400079988', 29.99, 1869),
                                                            ('Anna Karenina', 6, '9780143035008', 19.99, 1877),
                                                            ('The Great Gatsby', 7, '9780743273565', 13.99, 1925),
                                                            ('Tender Is the Night', 7, '9780684801544', 15.50, 1934),
                                                            ('To Kill a Mockingbird', 8, '9780061120084', 14.99, 1960),
                                                            ('The Adventures of Huckleberry Finn', 9, '9780486280615', 12.99, 1884),
                                                            ('The Adventures of Tom Sawyer', 9, '9780486400778', 11.99, 1876),
                                                            ('Pride and Prejudice', 10, '9780141439518', 10.99, 1813),
                                                            ('Sense and Sensibility', 10, '9780141439662', 11.50, 1811),
                                                            ('Emma', 10, '9780141439587', 12.99, 1815);

SELECT 'Authors Count' as Metric, COUNT(*) as Value FROM authors
UNION ALL
SELECT 'Books Count', COUNT(*) FROM books
UNION ALL
SELECT 'Average Book Price', CONCAT('$', FORMAT(AVG(price), 2)) FROM books
UNION ALL
SELECT 'Oldest Book Year', MIN(year) FROM books
UNION ALL
SELECT 'Newest Book Year', MAX(year) FROM books;


