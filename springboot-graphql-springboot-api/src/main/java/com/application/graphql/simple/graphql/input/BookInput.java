package com.application.graphql.simple.graphql.input;

public record BookInput(String isbn, String title, Integer year, Long authorId) {
}
