package org.example.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "authorship")
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


    public Authorship(){
        super();
    }
}

