package com.example.demo.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookRequestDTO;
import com.example.demo.dto.BookResponseDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Category;
import com.example.demo.model.Publisher;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PublisherRepository;
import com.example.demo.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Endpoints for managing books in the library")
@SecurityRequirement(name = "bearerAuth")

public class BookController {

    private final BookService bookService;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    
    public BookController(BookService bookService,
            CategoryRepository categoryRepository,
            PublisherRepository publisherRepository,
            AuthorRepository authorRepository,
            BookRepository bookRepository) {
    	
    			this.bookService = bookService;
    			this.categoryRepository = categoryRepository;
    			this.publisherRepository = publisherRepository;
    			this.authorRepository = authorRepository;
    			this.bookRepository = bookRepository;
    	}

    @Operation(summary = "Get all books", description = "Returns a list of all books in the system")   
    @GetMapping	
    public List<BookResponseDTO> getAllBooks() {
        List<Book> books = bookService.getAllBooks();

        return books.stream().map(book -> {
            BookResponseDTO dto = new BookResponseDTO();
            dto.setId(book.getId());
            dto.setTitle(book.getTitle());
            dto.setIsbn(book.getIsbn());
            dto.setEdition(book.getEdition());
            dto.setPublicationYear(book.getPublicationYear());
            dto.setLanguage(book.getLanguage());
            dto.setSummary(book.getSummary());
            dto.setCoverImageUrl(book.getCoverImageUrl());

            dto.setPublisherName(
            	    book.getPublisher() != null ? book.getPublisher().getName() : null
            	);
            List<String> authorNames = book.getAuthors().stream()
                .map(author -> author.getName())
                .toList();

            dto.setAuthorNames(authorNames);

            return dto;
        }).toList();
    }
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get book by ID" , description = "Returns a single book by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setEdition(book.getEdition());
        dto.setLanguage(book.getLanguage());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setSummary(book.getSummary());
        dto.setCoverImageUrl(book.getCoverImageUrl());
        dto.setPublisherName(
        	    book.getPublisher() != null ? book.getPublisher().getName() : null
        	);
        List<String> authorNames = book.getAuthors().stream()
                .map(Author::getName)
                .toList();

        dto.setAuthorNames(authorNames);

        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Create a new book", description = "Adds a new book to the database")
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO requestDTO) {
        Book book = new Book();
        book.setTitle(requestDTO.getTitle());
        book.setIsbn(requestDTO.getIsbn());
        book.setEdition(requestDTO.getEdition());
        book.setLanguage(requestDTO.getLanguage());
        book.setPublicationYear(requestDTO.getPublicationYear());
        book.setSummary(requestDTO.getSummary());
        book.setCoverImageUrl(requestDTO.getCoverImageUrl());

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

        Publisher publisher = publisherRepository.findById(requestDTO.getPublisherId())
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));
        book.setPublisher(publisher);

        List<Author> authors = authorRepository.findAllById(requestDTO.getAuthorIds());
        book.setAuthors(new HashSet<>(authors));

        Book savedBook = bookRepository.save(book);

        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(savedBook.getId());
        dto.setTitle(savedBook.getTitle());
        dto.setIsbn(savedBook.getIsbn());
        dto.setEdition(savedBook.getEdition());
        dto.setLanguage(savedBook.getLanguage());
        dto.setPublicationYear(savedBook.getPublicationYear());
        dto.setSummary(savedBook.getSummary());
        dto.setCoverImageUrl(savedBook.getCoverImageUrl());
        dto.setPublisherName(
        	    savedBook.getPublisher() != null ? savedBook.getPublisher().getName() : null
        	);
        List<String> authorNames = savedBook.getAuthors().stream()
                .map(Author::getName)
                .toList();

        dto.setAuthorNames(authorNames);

        return ResponseEntity.ok(dto);
    }
    
    @Operation(summary = "Update a book", description = "Updates details of an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO requestDTO) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(requestDTO.getTitle());
        book.setIsbn(requestDTO.getIsbn());
        book.setEdition(requestDTO.getEdition());
        book.setLanguage(requestDTO.getLanguage());
        book.setPublicationYear(requestDTO.getPublicationYear());
        book.setSummary(requestDTO.getSummary());
        book.setCoverImageUrl(requestDTO.getCoverImageUrl());

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

        Publisher publisher = publisherRepository.findById(requestDTO.getPublisherId())
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));
        book.setPublisher(publisher);

        List<Author> authors = authorRepository.findAllById(requestDTO.getAuthorIds());
        book.setAuthors(new HashSet<>(authors));

        Book updatedBook = bookRepository.save(book);

        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(updatedBook.getId());
        dto.setTitle(updatedBook.getTitle());
        dto.setIsbn(updatedBook.getIsbn());
        dto.setEdition(updatedBook.getEdition());
        dto.setLanguage(updatedBook.getLanguage());
        dto.setPublicationYear(updatedBook.getPublicationYear());
        dto.setSummary(updatedBook.getSummary());
        dto.setCoverImageUrl(updatedBook.getCoverImageUrl());
        dto.setPublisherName(
        	    updatedBook.getPublisher() != null ? updatedBook.getPublisher().getName() : null
        	);
        List<String> authorNames = updatedBook.getAuthors().stream()
                .map(Author::getName)
                .toList();

        dto.setAuthorNames(authorNames);

        return ResponseEntity.ok(dto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a book", description = "Deletes a book by its ID")
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
