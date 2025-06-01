**Library Management System**
**Overview**
This project is a Library Management System built with Java Spring Boot and a relational database. It provides RESTful APIs to manage books, members, system users, and borrowing transactions with role-based access control and authentication.

**Features**
Book Management: Create, read, update, and delete books with detailed metadata (authors, publishers, categories, language, publication year, ISBN, edition, summary, cover images).

Member Management: Manage library members (borrowers).

User Management: Admins, Librarians, and Staff roles with specific permissions.

Borrowing Transactions: Borrow and return books with status tracking.

Role-Based Access Control: Secure endpoints based on user roles.

Authentication: JWT-based authentication with secure password storage.

User Activity Logging (optional/extendable).

Technologies Used

- Java 17
- 
- Spring Boot
- 
- Spring Security (JWT)
- 
- Spring Data JPA
- 
- Hibernate
- 
- MySQL
- 
- Maven
- 
- Lombok


Database Schema
Books, Authors, Publishers, Categories (with hierarchical categories)

Members and System Users with roles (ADMIN, LIBRARIAN, STAFF)

Borrowing transactions table to track book loans and returns

Refer to library_management.sql for the full schema and sample data.

API Endpoints

- /api/books - CRUD operations on books
- 
- /api/members - CRUD operations on members
- 
- /api/users - Manage system users and roles
- 
- /api/borrows - Borrow and return books
- 
- /api/auth - Authentication endpoints

Role-based restrictions applied to protect sensitive endpoints.

Setup Instructions
Clone the repository:

bash

```
git clone https://github.com/yourusername/library-management.git
cd library-management
Import the database schema and sample data:
```

bash

```
mysql -u your_user -p library_management < library_management.sql
Configure your database connection in application.properties or application.yml.

```
Build and run the Spring Boot application:

bash
```
mvn clean install
mvn spring-boot:run
Access the API at: http://localhost:8080/api
```

Postman Collection
A Postman collection is included for easy testing of API endpoints (library_management.postman_collection.json).

Design Choices
Role-Based Access Control implemented using Spring Security and method-level annotations.

JWT Authentication to provide stateless security.

DTOs for request and response payloads to separate internal model from external API contract.

Hierarchical categories implemented with a self-referencing relationship.

Use of Sets for Authors to avoid duplicates.

Validation and error handling can be extended for robustness.

