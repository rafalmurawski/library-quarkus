package com.murawski.library.api;

import com.murawski.library.model.AddBookCommand;
import com.murawski.library.model.AddCopyCommand;
import com.murawski.library.model.entity.Author;
import com.murawski.library.model.entity.Book;
import com.murawski.library.model.entity.Copy;
import com.murawski.library.service.BookService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookApi {

    private BookService bookService;

    @Inject
    public BookApi(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @RolesAllowed("USER")
    public List<Book> getBookList() {
        return bookService.showAllBooks();
    }

    @GET
    @RolesAllowed("USER")
    @Path("/authors")
    public List<Author> getAuthorList() {
        return bookService.showAllAuthors();
    }

    @POST
    @RolesAllowed({"ADMIN"})
    public void addBook(AddBookCommand addBookCommand) {
        bookService.addBook(addBookCommand);
    }

    @POST
    @RolesAllowed("ADMIN")
    @Path("/copies")
    public void addCopy(AddCopyCommand addCopyCommand) {
        bookService.addCopy(addCopyCommand);
    }

    @GET
    @RolesAllowed("USER")
    @Path("/copies")
    public List<Copy> showAllCopies() {
        return bookService.showAllCopies();
    }

    @PATCH
    @Path("/copies")
    @RolesAllowed("USER")
    public void rentCopy(@Context SecurityContext securityContext, @QueryParam("isbn") String isbn) {
        bookService.rentCopy(securityContext, isbn);


    }


}
