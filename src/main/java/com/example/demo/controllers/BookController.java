package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.stream.Collectors.toCollection;

@Controller
@RequiredArgsConstructor
public class BookController {

    @Value("${upload.path}")
    private String uploadPath;

    private final BookRepository bookRepository;

    @RequestMapping({"/", ""})
    public String index() {
        return "redirect:/books-list";
    }

    @GetMapping("/books-list")
    public String booksList(@RequestParam(required = false) String filter, Model model) {
        List<Book> filtered = filter == null ? bookRepository.getBooks() :
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
    public String saveNewBook(@ModelAttribute Book book, @RequestParam MultipartFile file, Model model) throws IOException {
        if ((book != null) && !book.getIsbn().equals("") && !book.getTitle().equals("") &&
                !book.getAuthor().equals("") && book.getPublishingYear() != null && !book.getDescription().equals("") &&
                file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));

            book.setFilename(resultFileName);
            bookRepository.addBook(book);
        }
        return "redirect:/books-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        if (!(id == null) && bookRepository.findById(id) != null) {
            new File(uploadPath + "/" + bookRepository.findById(id).getFilename()).delete();
            bookRepository.deleteBook(id);
        }
        return "redirect:/books-list";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable Integer id, Model model) {
        Book book = bookRepository.findById(id);
        model.addAttribute("book", book);
        return "book";
    }
}

