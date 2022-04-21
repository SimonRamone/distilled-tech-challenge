package org.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String surname;

    @OneToMany(mappedBy ="author", cascade = CascadeType.ALL)
    Set<Authorship> books;

    public Author(){
        super();
    }
}
