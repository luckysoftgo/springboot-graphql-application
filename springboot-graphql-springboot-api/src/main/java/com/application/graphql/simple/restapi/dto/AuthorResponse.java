package com.application.graphql.simple.restapi.dto;

import com.application.graphql.simple.model.Author;

public record AuthorResponse(Long id, String name) {

    public static AuthorResponse from(Author author) {
        return new AuthorResponse(author.getId(), author.getName());
    }
}
