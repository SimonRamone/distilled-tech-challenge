package org.example.repository;

import org.example.exception.BookNotFoundException;
import org.example.model.Authorship;
import org.example.model.Book;
import org.example.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}