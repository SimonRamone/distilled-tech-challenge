package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "authorships")
public class Authorship {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne()
    @JoinColumn(name = "book")
    private Book book;

    @ManyToOne()
    @JoinColumn(name = "author")
    private Author author;

    public Authorship() {
        super();
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }
}

