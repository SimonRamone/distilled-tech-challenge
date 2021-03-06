package org.example.controller;

import org.example.exception.AuthorNotFoundException;
import org.example.exception.BookNotFoundException;
import org.example.exception.InvalidBookEntryException;
import org.example.model.Author;
import org.example.model.Authorship;
import org.example.model.Book;
import org.example.repository.AuthorRepository;
import org.example.repository.AuthorshipRepository;
import org.example.repository.BookRepository;
import org.example.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorshipRepository authorshipRepository;
    @Autowired
    BorrowRepository borrowRepository;

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public @ResponseBody
    Collection<Book> listBooks(@RequestParam(defaultValue = "") String isbn,
                               @RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String author) throws BookNotFoundException {
        if (!isbn.isEmpty()) {
            return bookRepository.findAllByIsbn(isbn);
        }
        if (!title.isEmpty()) {
            return bookRepository.findAllByTitle(title);
        }
        if (!author.isEmpty()) {
            return authorshipRepository.findBooksByAuthor(author);
        }
        return bookRepository.findAll();
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws URISyntaxException {
        bookRepository.save(book);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/books/" + book.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Book getBook(@PathVariable Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) throw new BookNotFoundException(id);
        return book.get();
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) throws BookNotFoundException, InvalidBookEntryException {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) throw new BookNotFoundException(id);

        if (updatedBook.getId() != null && !id.equals(updatedBook.getId()))
            throw new InvalidBookEntryException();

        book.get().update(updatedBook);
        bookRepository.updateBookById(book.get().getTitle(), book.get().getIsbn(), book.get().getStatus(), id);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/books/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Location", path);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/books/{id}/updateStatus", method = RequestMethod.PATCH)
    public void updateBookStatus(@PathVariable Long id, @RequestParam(defaultValue = "") String status) throws BookNotFoundException, InvalidBookEntryException {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) throw new BookNotFoundException(id);

        if (!status.equals("BORROWED") && !status.equals("AVAILABLE"))
            throw new InvalidBookEntryException();

        book.get().setStatus(status);
        bookRepository.updateBookStatusById(book.get().getStatus(), id);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) throw new BookNotFoundException(id);
        bookRepository.deleteById(id);
    }

    @RequestMapping(value = "books/{id}/addAuthor", method = RequestMethod.POST)
    public ResponseEntity<Book> addAuthor(@PathVariable(value = "id") Long bookId, @RequestParam(defaultValue = "") Long authorId) throws BookNotFoundException, AuthorNotFoundException, URISyntaxException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));

        authorshipRepository.save(new Authorship(book, author));

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/books/" + book.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }
}
