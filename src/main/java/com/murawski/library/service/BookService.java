package com.murawski.library.service;

import com.murawski.library.model.AddAuthorCommand;
import com.murawski.library.model.AddBookCommand;
import com.murawski.library.model.entity.Author;
import com.murawski.library.model.entity.Book;
import com.murawski.library.model.repository.AuthorRepository;
import com.murawski.library.model.repository.BookRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Inject
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> showAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void addBook(AddBookCommand addBookCommand) {
        List<Author> authors = addBookCommand.getAuthorList().stream()
                .map(this::addAuthor)
                .collect(toList());

        bookRepository.save(new Book(addBookCommand.getIsbn(), addBookCommand.getTitle().toUpperCase(), authors));

    }


    public Optional<Author> findAuthorByAuthorCommand(AddAuthorCommand addAuthorCommand) {
        return authorRepository.findByNameAndLastName(addAuthorCommand.getName().toUpperCase(), addAuthorCommand.getLastName().toUpperCase());
    }

    public Author addAuthor(AddAuthorCommand addAuthorCommand) {
        return findAuthorByAuthorCommand(addAuthorCommand)
                .orElseGet(() -> authorRepository.save(new Author(addAuthorCommand.getName().toUpperCase(), addAuthorCommand.getLastName().toUpperCase())));
    }

}