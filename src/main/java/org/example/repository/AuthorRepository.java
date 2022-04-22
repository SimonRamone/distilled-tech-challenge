package org.example.repository;

import org.example.exception.AuthorNotFoundException;
import org.example.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE a.name = :name")
    List<Author> findAllByName(String name) throws AuthorNotFoundException;
}
