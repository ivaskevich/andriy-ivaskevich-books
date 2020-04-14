package com.example.demo.exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(long book_id) {
        super(String.format("Book with id  '%s' is not found !", book_id));
    }
}
