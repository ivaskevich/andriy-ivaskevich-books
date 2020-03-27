package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
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

    @GetMapping({"/"})
    public String index() {
        return "books";
    }

    @ResponseBody
    @GetMapping("/books")
    public List<Book> booksList(@RequestParam(required = false) String filter) {
        return filter == null || filter.equals("") ? bookRepository.findAll() : bookRepository.findAll().stream()
                .filter(b -> b.toString().contains(filter)).collect(toCollection(ArrayList::new));
    }

    @GetMapping("/books/{id}")
    public String getBook(@PathVariable Integer id, Model model) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        model.addAttribute("book", book);
        return "book";
    }
}
