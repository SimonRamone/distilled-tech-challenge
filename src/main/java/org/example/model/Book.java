package org.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    List<Authorship> authors;
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    @NotBlank
    private String status;

    public Book() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Authorship> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Authorship> authors) {
        this.authors = authors;
    }

    public void update(Book book) {
        if (book.isbn != null) isbn = book.isbn;
        if (book.title != null) title = book.title;
        if (book.status != null) status = book.status;
        if (book.authors != null) authors = book.authors;
    }
}