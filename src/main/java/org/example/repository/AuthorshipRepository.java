package org.example.repository;

import org.example.exception.BookNotFoundException;
import org.example.model.Authorship;
import org.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorshipRepository extends JpaRepository<Authorship, Long> {
    @Query("select a.book from Authorship a where a.author = :author")
    List<Book> findBooksByAuthor(String author) throws BookNotFoundException;
}



