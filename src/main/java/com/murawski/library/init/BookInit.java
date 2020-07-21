package com.murawski.library.init;

import com.murawski.library.model.AddAuthorCommand;
import com.murawski.library.model.AddBookCommand;
import com.murawski.library.service.BookService;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class BookInit {

    private BookService bookService;

    @Inject
    public BookInit(BookService bookService) {
        this.bookService = bookService;
    }

    public void addFirstBooks(@Observes StartupEvent startupEvent) {


        AddAuthorCommand gajda = new AddAuthorCommand("Włodzimierz", "Gajda");
        AddAuthorCommand martin = new AddAuthorCommand("Robert C.", "Martin");
        AddAuthorCommand hoover = new AddAuthorCommand("Dave H.", "Hoover");
        AddAuthorCommand oshineye = new AddAuthorCommand("Adewale", "Oshineye");


        bookService.addBook(new AddBookCommand("Git. Rozproszony system kontroli wersji", "978-83-24655-64-9", List.of(gajda)));
        bookService.addBook(new AddBookCommand("Czysty kod. Podręcznik dobrego programisty", "978-83-28302-34-1", List.of(martin)));
        bookService.addBook(new AddBookCommand("Praktyka czyni mistrza.", "978-83-28335-21-9", List.of(oshineye, hoover)));


    }
}
