package org.example.controller;

import org.example.exception.*;
import org.example.model.Author;
import org.example.model.Authorship;
import org.example.model.Book;
import org.example.model.Borrow;
import org.example.repository.AuthorRepository;
import org.example.repository.AuthorshipRepository;
import org.example.repository.BookRepository;
import org.example.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
public class BorrowController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BorrowRepository borrowRepository;

    @RequestMapping(value = "/borrows", method = RequestMethod.GET)
    public @ResponseBody
    Collection<Borrow> listBorrows() {
        return borrowRepository.findAll();
    }

    @RequestMapping(value = "/borrows", method = RequestMethod.POST)
    public ResponseEntity<Borrow> addBorrow(@RequestParam(defaultValue = "") Long bookId, @RequestParam(defaultValue = "") String username) throws BookNotFoundException, URISyntaxException, BookNotAvailableException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if(book.getStatus().equals("BORROWED")) throw new BookNotAvailableException(bookId);

        Borrow borrow = new Borrow(username, book);
        borrowRepository.save(borrow);

        book.setStatus("BORROWED");
        bookRepository.updateBookStatusById(book.getStatus(), bookId);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/borrows/" + borrow.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(borrow, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/borrows/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Borrow getBorrow(@PathVariable Long id) throws BorrowNotFoundException {
        Optional<Borrow> borrow = borrowRepository.findById(id);
        if (!borrow.isPresent()) throw new BorrowNotFoundException(id);
        return borrow.get();
    }
}
