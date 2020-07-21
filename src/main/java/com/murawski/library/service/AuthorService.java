package com.murawski.library.service;

import com.murawski.library.model.entity.Author;
import com.murawski.library.model.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Inject
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public List<Author> showAllAuthors() {
        return authorRepository.findAll();
    }
}
