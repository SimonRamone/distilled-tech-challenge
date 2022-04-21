package org.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    @NotBlank
    private String status;

    @OneToMany(mappedBy ="book", cascade = CascadeType.ALL)
    Set<Authorship> authors;

    public Book(){
        super();
    }
}