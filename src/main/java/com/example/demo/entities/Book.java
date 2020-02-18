package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book {
    private String isbn;
    private String title;
    private String author;
    private Integer publishingYear;

    @Override
    public String toString() {
        return " " + isbn + " " + title + " " + author +" " + publishingYear;
    }
}
