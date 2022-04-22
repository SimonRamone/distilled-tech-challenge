package org.example.repository;

import org.example.exception.BookNotFoundException;
import org.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.title = :title")
    List<Book> findAllByTitle(String title) throws BookNotFoundException;

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
    List<Book> findAllByIsbn(String isbn) throws BookNotFoundException;

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.title = :title, b.isbn = :isbn, b.status = :status where b.id = :id")
    int updateBookById(String title, String isbn, String status, Long id);
}