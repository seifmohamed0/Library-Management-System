-- Create tables

CREATE TABLE publishers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    FOREIGN KEY (parent_id) REFERENCES categories(id)
);

CREATE TABLE authors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    edition VARCHAR(100),
    isbn VARCHAR(50),
    language VARCHAR(50),
    publication_year INT,
    summary TEXT,
    cover_image_url VARCHAR(255),
    publisher_id BIGINT,
    category_id BIGINT,
    FOREIGN KEY (publisher_id) REFERENCES publishers(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE book_authors (
    book_id BIGINT,
    author_id BIGINT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE TABLE members (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50)
);

CREATE TABLE system_users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE borrows (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id BIGINT,
    member_id BIGINT,
    borrow_date DATE,
    return_date DATE,
    due_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Insert sample data

INSERT INTO publishers (name) VALUES ('Penguin Random House'), ('HarperCollins'), ('O\'Reilly Media');

INSERT INTO categories (name, parent_id) VALUES 
    ('Fiction', NULL),
    ('Science Fiction', 1),
    ('Non-Fiction', NULL),
    ('Technology', 3);

INSERT INTO authors (name) VALUES ('Isaac Asimov'), ('George Orwell'), ('J.K. Rowling');

INSERT INTO books (title, edition, isbn, language, publication_year, summary, cover_image_url, publisher_id, category_id) VALUES
    ('Foundation', '1st', '1234567890', 'English', 1951, 'A science fiction novel.', 'foundation.jpg', 1, 2),
    ('1984', '1st', '0987654321', 'English', 1949, 'Dystopian novel.', '1984.jpg', 2, 2),
    ('Harry Potter and the Sorcerer\'s Stone', '1st', '1122334455', 'English', 1997, 'Fantasy novel.', 'hp1.jpg', 1, 1);

INSERT INTO book_authors (book_id, author_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3);

INSERT INTO members (name, email, phone) VALUES
    ('Alice Johnson', 'alice@example.com', '1234567890'),
    ('Bob Smith', 'bob@example.com', '0987654321');

INSERT INTO system_users (username, password, role) VALUES
    ('admin', '$2a$10$7wNqPhY89pV5a3kQqWdlUO8fX3pq9/qTb6G8TnY8zJxvGpJ1lrIu2', 'ROLE_ADMIN'),
    ('librarian', '$2a$10$7wNqPhY89pV5a3kQqWdlUO8fX3pq9/qTb6G8TnY8zJxvGpJ1lrIu2', 'ROLE_LIBRARIAN');

INSERT INTO borrows (book_id, member_id, borrow_date, due_date, status) VALUES
    (1, 1, '2025-05-01', '2025-05-15', 'BORROWED'),
    (2, 2, '2025-05-10', '2025-05-24', 'RETURNED');
