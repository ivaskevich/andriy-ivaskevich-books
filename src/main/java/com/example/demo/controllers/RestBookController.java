package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestBookController {

    private final BookRepository bookRepository;

    @PostMapping(value = "/books")
    public ResponseEntity<Book> saveNewBook(@RequestBody final Book book) {
        if ((book != null) && !book.getIsbn().equals("") && !book.getTitle().equals("") &&
                !book.getAuthor().equals("") && !book.getPublishingYear().equals("") && !book.getDescription().equals(""))
            bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/books/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return ResponseEntity.ok(book);
    }

    @PostMapping("/books/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody final Book bookDetails) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setDescription(bookDetails.getDescription());
        book.setPublishingYear(bookDetails.getPublishingYear());
        book.setFilename(bookDetails.getFilename());

        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/books/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Integer id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.delete(book);
        return ResponseEntity.ok(book);
    }
}