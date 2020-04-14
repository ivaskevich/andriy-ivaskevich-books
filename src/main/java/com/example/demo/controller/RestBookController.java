package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestBookController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @PostMapping("/admin/books")
    public ResponseEntity saveNewBook(@RequestBody final Book book) {
        if (!book.getIsbn().matches(
                "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$"))
            return new ResponseEntity<>("isbnError", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        if (!book.getTitle().equals("") && !book.getAuthor().equals("") && !book.getPublishingYear().equals("") &&
                !book.getDescription().equals(""))
            bookRepository.save(book);
        else return new ResponseEntity<>("Error", new HttpHeaders(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/admin/books/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return ResponseEntity.ok(book);
    }

    @PostMapping("/admin/books/update/{id}")
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

    @PostMapping("/admin/books/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Integer id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.delete(book);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/favorite/add/{bookId}")
    public ResponseEntity<Book> addToFavorites(@PathVariable Integer bookId)
            throws BookNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        User user = userRepository.findByUsername(userDetails.getUsername());
        user.getFavoriteBooks().add(book);
        userRepository.save(user);

        return ResponseEntity.ok(book);
    }

    @PostMapping("/favorite/delete/{bookId}")
    public ResponseEntity<Book> deleteFromFavorites(@PathVariable Integer bookId)
            throws BookNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        User user = userRepository.findByUsername(userDetails.getUsername());
        user.getFavoriteBooks().remove(book);
        userRepository.save(user);

        return ResponseEntity.ok(book);
    }
}