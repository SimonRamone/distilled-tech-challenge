package org.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrows")
public class Borrow {
    @OneToOne
    private Book book;
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String username;

    private LocalDateTime dateAndTime;

    public Borrow() {
        super();
    }

    public Borrow(String username, Book book) {
        super();
        this.username = username;
        this.book = book;
        this.dateAndTime = LocalDateTime.now();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}