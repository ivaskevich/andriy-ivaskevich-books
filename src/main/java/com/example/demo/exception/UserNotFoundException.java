package com.example.demo.exception;


public class UserNotFoundException extends Exception {
    public UserNotFoundException(long book_id) {
        super(String.format("User with id '%s' is not found !", book_id));
    }
}