package com.murawski.library.service;

import com.murawski.library.model.AddAuthorCommand;
import com.murawski.library.model.AddBookCommand;
import com.murawski.library.model.AddCopyCommand;
import com.murawski.library.model.entity.Author;
import com.murawski.library.model.entity.Book;
import com.murawski.library.model.entity.Copy;
import com.murawski.library.model.repository.AuthorRepository;
import com.murawski.library.model.repository.BookRepository;
import com.murawski.library.model.repository.CopyRepository;
import com.murawski.user.model.entity.User;
import com.murawski.user.model.repo.UserRepsitory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CopyRepository copyRepository;
    private UserRepsitory userRepsitory;

    @Inject
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, CopyRepository copyRepository, UserRepsitory userRepsitory) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.copyRepository = copyRepository;
        this.userRepsitory = userRepsitory;
    }

    public List<Book> showAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void addBook(AddBookCommand addBookCommand) {
        List<Author> authors = addBookCommand.getAuthorList().stream()
                .map(this::addAuthor)
                .collect(toList());

        bookRepository.save(new Book(addBookCommand.getIsbn(), addBookCommand.getTitle(), authors));

    }


    public List<Author> showAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorByAuthorCommand(AddAuthorCommand addAuthorCommand) {
        return authorRepository.findByNameAndLastName(addAuthorCommand.getName(), addAuthorCommand.getLastName());
    }

    public Author addAuthor(AddAuthorCommand addAuthorCommand) {
        return findAuthorByAuthorCommand(addAuthorCommand)
                .orElseGet(() -> authorRepository.save(new Author(addAuthorCommand.getName(), addAuthorCommand.getLastName())));
    }

    //Todo
    @Inject
    public void addFirstBooks() {
        var authors = List.of(
                new AddAuthorCommand("Adam", "Mickiewicz"),
                new AddAuthorCommand("Henryk", "Sienkiewicz"));

        addBook(new AddBookCommand("TESTOWA", "978-83-61492-24-5", authors));


    }


    @Transactional
    public void addCopy(AddCopyCommand addCopyCommand) {
        bookRepository.findAllByIsbn(addCopyCommand.getIsbn())
                .ifPresent(book -> addCopies(addCopyCommand.getQuantity(), book));
    }

    @Transactional
    private void addCopies(int quantity, Book book) {
        Random random = new Random();
        LocalDate date = LocalDate.now();
        IntStream.range(0, quantity)
                .forEach(notUsed ->
                        copyRepository.save(new Copy(random.nextInt(1000) + "/" + date.getYear(), book)));
    }


    @Transactional
    public void rentCopy(SecurityContext securityContext, String isbn) {
        Optional<User> loggedUser = userRepsitory.findByEmail(securityContext.getUserPrincipal().getName());
        bookRepository.findAllByIsbn(isbn)
                .map(book -> copyRepository.findAllByBook(book))
                .flatMap(copies -> copies.stream()
                        .filter(copy -> copy.getUser() == null)
                        .findFirst())
                .map(copy -> {
                    copy.setUser(loggedUser.get());
                    return copyRepository.save(copy);
                })
                .orElseThrow(() -> new RuntimeException("no books to rent"));

    }


    public List<Copy> showAllCopies() {
        return copyRepository.findAll();

    }
}