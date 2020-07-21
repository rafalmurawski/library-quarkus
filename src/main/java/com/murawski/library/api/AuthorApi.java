package com.murawski.library.api;

import com.murawski.library.model.entity.Author;
import com.murawski.library.service.AuthorService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorApi {

    private AuthorService authorService;

    @Inject
    public AuthorApi(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GET
    @RolesAllowed("ADMIN")
    public List<Author> getAuthorList() {
        return authorService.showAllAuthors();
    }
}
