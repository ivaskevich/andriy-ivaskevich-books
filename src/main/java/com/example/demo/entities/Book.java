package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String isbn;
    private String title;
    private String author;
    private Integer publishingYear;

    @Override
    public String toString() {
        return " " + isbn + " " + title + " " + author +" " + publishingYear;
    }
}