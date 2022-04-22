package org.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Authorship> books;
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;

    public Author() {
        super();
    }

    public Author(String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
