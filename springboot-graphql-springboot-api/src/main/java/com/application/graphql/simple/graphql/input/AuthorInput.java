package com.application.graphql.simple.graphql.input;

import com.application.graphql.simple.model.Book;

import java.util.Set;

public record AuthorInput(String name, Set<Book> books) {

}
