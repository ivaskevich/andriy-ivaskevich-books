package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String publishingYear;

    @JsonIgnore
    @ManyToMany(mappedBy = "favoriteBooks")
    private Collection<User> usersFavorite;

    @NotBlank
    private String description;
    @NotBlank
    private String filename;

    public Book(@NotBlank String isbn, @NotBlank String title, @NotBlank String author,
                @NotBlank String publishingYear, @NotBlank String description, @NotBlank String filename) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.description = description;
        this.filename = filename;
    }

    public boolean isFavorite(String username) {
        for (User user : usersFavorite) if (user.getUsername().equals(username)) return true;
        return false;
    }

    @Override
    public String toString() {
        return " " + isbn + " " + title + " " + author + " " + publishingYear;
    }
}
