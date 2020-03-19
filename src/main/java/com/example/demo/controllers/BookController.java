package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @RequestMapping({"/", ""})
    public String index() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String booksList(@RequestParam(required = false) String filter, Model model) {
        List<Book> filtered = filter == null ? bookRepository.findAll() :
                bookRepository.findAll().stream()
                        .filter(b -> b.toString().contains(filter)).collect(toCollection(ArrayList::new));

        if (filtered.size() == 0) {
            if (filter == null) model.addAttribute("alert", "There are no books yet !");
            else model.addAttribute("alert", "Your search did not match any results !");
        }
        model.addAttribute("books", filtered);
        return "books";
    }

    @PostMapping("/books")
    public String saveNewBook(@ModelAttribute Book book) {
        if ((book != null) && !book.getIsbn().equals("") && !book.getTitle().equals("") &&
                !book.getAuthor().equals("") && !book.getPublishingYear().equals("") && !book.getDescription().equals(""))
            bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String getBook(@PathVariable Integer id, Model model) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/books/update/{id}")
    public String updateBook(@PathVariable Integer id, Model model) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        model.addAttribute("book", book);
        return "books";
    }

    @PostMapping("/books/update/{id}")
    public String updateBook(@PathVariable Integer id, @ModelAttribute Book bookDetails) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setDescription(bookDetails.getDescription());
        book.setPublishingYear(bookDetails.getPublishingYear());
        book.setFilename(bookDetails.getFilename());

        bookRepository.save(book);
        return "redirect:/books";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Integer id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.delete(book);
        return "redirect:/books";
    }
}