package com.application.graphql.simple.service;

import com.application.graphql.simple.graphql.input.AuthorInput;
import com.application.graphql.simple.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAuthors();

    Author validateAndGetAuthorById(Long id);

    List<Author> getAuthorByIds(List<Long> ids);

    List<Author> validateAndGetAuthorByName(String name);

    Author saveAuthor(Author author);

    Boolean saveAuthors(List<AuthorInput> authorInputs);

    void deleteAuthor(Author author);
}
