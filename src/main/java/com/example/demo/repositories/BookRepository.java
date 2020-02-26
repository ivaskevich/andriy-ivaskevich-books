package com.example.demo.repositories;

import com.example.demo.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager entityManager;

    private final ArrayList<Book> books = new ArrayList<>();

    @Transactional
    public Book addBook(Book book) {
        return entityManager.merge(book);
    }

    @Transactional
    public Book findById(Integer id) {
        return entityManager.find(Book.class, id);
    }

    @Transactional
    public void deleteBook(Integer id) {
        if (id != null) {
            Book b = findById(id);
            if (b != null) books.remove(b);
        }
    }

    @Transactional
    public ArrayList<Book> getBooks() {
        return new ArrayList<>(books);
    }


}