package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;

@Controller
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private final BookRepository bookRepository;

    @RequestMapping({"/", ""})
    public String index() {
        return "redirect:/books-list";
    }

    @GetMapping("/books-list")
    public String booksList(@RequestParam(required = false) String filter, Model model) {
        ArrayList<Book> filtered = filter == null ? bookRepository.getBooks() :
                bookRepository.getBooks().stream()
                        .filter(b -> b.toString().contains(filter)).collect(toCollection(ArrayList::new));

        if (filtered.size() == 0) {
            if (filter == null) model.addAttribute("alert", "There are no books yet !");
            else model.addAttribute("alert", "Your search did not match any results !");
        }
        model.addAttribute("books", filtered);
        return "books";
    }

    @PostMapping("/add-book")
    public String saveNewBook(@ModelAttribute Book book, Model model) {
        if ((book != null) && !book.getIsbn().equals("") && !book.getTitle().equals("") &&
        !book.getAuthor().equals("") && !book.getPublishingYear().equals("")) {
            bookRepository.addBook(book);
        }
        return "redirect:/books-list";
    }

    @GetMapping("/delete/{isbn}")
    public String deleteBook(@PathVariable String isbn) {
        if (!(isbn == null) && !isbn.equals("")) bookRepository.deleteBook(isbn);
        return "redirect:/books-list";
    }
}

