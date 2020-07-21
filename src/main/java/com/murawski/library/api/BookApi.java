package com.murawski.library.api;

import com.murawski.library.model.AddBookCommand;
import com.murawski.library.model.entity.Book;
import com.murawski.library.service.BookService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    @RolesAllowed({"USER", "ADMIN"})
    public List<Book> getBookList() {
        return bookService.showAllBooks();
    }

    @POST
    @RolesAllowed("ADMIN")
    public void addBook(AddBookCommand addBookCommand) {
        bookService.addBook(addBookCommand);
    }

}
