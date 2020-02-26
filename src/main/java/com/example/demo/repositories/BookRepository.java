package com.example.demo.repositories;

import com.example.demo.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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
        if(id!=null) entityManager.remove(findById(id));
    }

    @Transactional
    public List<Book> getBooks() {
        return entityManager.createQuery("SELECT a FROM  Book a",Book.class).getResultList();
    }


}