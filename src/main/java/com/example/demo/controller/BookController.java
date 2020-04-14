package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @GetMapping("/admin/books")
    public String adminPage() {
        return "books";
    }

    @ResponseBody
    @GetMapping("/book-list")
    public List<Book> booksList(@RequestParam(required = false) String filter) {
        return filter == null || filter.equals("") ? bookRepository.findAll() : bookRepository.findAll().stream()
                .filter(b -> b.toString().contains(filter)).collect(toCollection(ArrayList::new));
    }

    @ResponseBody
    @GetMapping("/user-books-id")
    public List<Integer> userBooks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals("anonymousUser")) return new ArrayList<>();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        List<Integer> booksId = new ArrayList<>();
        for (Book book : userRepository.findByUsername(userDetails.getUsername()).getFavoriteBooks()) {
            booksId.add(book.getId());
        }
        return booksId;
    }

    @ResponseBody
    @GetMapping("/favorite-book-list")
    public List<Book> favoriteBooksList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        return (List<Book>) user.getFavoriteBooks();
    }

    @GetMapping("/catalogue/book/{id}")
    public String getBook(@PathVariable Integer id, Model model) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/favorite")
    public String favoriteBooks() {
        return "favorite";
    }
}
