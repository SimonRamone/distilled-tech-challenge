package org.example.controller;

import org.example.exception.AuthorNotFoundException;
import org.example.model.Author;
import org.example.repository.AuthorRepository;
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
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody
    Collection<Author> listAuthors(@RequestParam(defaultValue = "") String name) throws AuthorNotFoundException {
        if (!name.isEmpty()) {
            return authorRepository.findAllByName(name);
        }
        return authorRepository.findAll();
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) throws URISyntaxException {
        authorRepository.save(author);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/authors/" + author.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(author, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/authors/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Author getAuthor(@PathVariable Long id) throws AuthorNotFoundException {
        Optional<Author> author = authorRepository.findById(id);
        if (!author.isPresent()) throw new AuthorNotFoundException(id);
        return author.get();
    }


    @RequestMapping(value = "/authors/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) throws AuthorNotFoundException {
        Optional<Author> author = authorRepository.findById(id);
        if (!author.isPresent()) throw new AuthorNotFoundException(id);
        authorRepository.deleteById(id);
    }
}
