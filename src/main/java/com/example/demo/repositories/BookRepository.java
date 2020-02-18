package com.example.demo.repositories;

import com.example.demo.entities.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class BookRepository {

    private final ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public Book findByIsbn(String isbn) {
        for (Book b : books
        ) {
            if (b.getIsbn().equals(isbn)) return b;
        }
        return null;
    }

    public void deleteBook(String isbn) {
        if (isbn != null) {
            Book b = findByIsbn(isbn);
            if (b != null) books.remove(b);
        }
    }

    public ArrayList<Book> getBooks() {
        return new ArrayList<>(books);
    }


}
