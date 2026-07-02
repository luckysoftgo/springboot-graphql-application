package com.application.graphql.simple.model;

public record Review(String reviewer, String comment, Integer rating, String createdAt) {
}
