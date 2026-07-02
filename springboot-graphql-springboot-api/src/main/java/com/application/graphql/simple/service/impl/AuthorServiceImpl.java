package com.application.graphql.simple.service.impl;

import com.application.graphql.simple.exception.AuthorNotFoundException;
import com.application.graphql.simple.graphql.input.AuthorInput;
import com.application.graphql.simple.model.Author;
import com.application.graphql.simple.repository.AuthorRepository;
import com.application.graphql.simple.service.AuthorService;
import com.application.graphql.simple.service.ComplexOperateComponent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ComplexOperateComponent operateComponent;

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author validateAndGetAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id '%s' not found", id)));
    }

    @Override
    public List<Author> getAuthorByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return List.of();
        } else {
            return authorRepository.findAllById(ids);
        }
    }

    @Override
    public List<Author> validateAndGetAuthorByName(String name) {
        return new ArrayList<>(authorRepository.findByNameContainingOrderByName(StringUtils.normalizeSpace(name)));
    }

    @Override
    public Author saveAuthor(Author author) {
        List<Author> authors = authorRepository.findByNameContainingOrderByName(StringUtils.normalizeSpace(author.getName()));
        if (CollectionUtils.isEmpty(authors)) {
            return operateComponent.saveAuthor(author);
        }
        return authors.get(0);
    }

    @Override
    public Boolean saveAuthors(List<AuthorInput> authors) {
       for (AuthorInput authorInput : authors) {
           Author author = Author.from(authorInput);
           saveAuthor(author);
       }
       return true;
    }

    @Override
    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }
}
