package com.application.graphql.remote.model;

import com.application.graphql.remote.graphql.input.ReviewInput;

import java.time.Instant;

public record Review(String reviewer, String comment, Integer rating, String createdAt) {

    public static Review from(ReviewInput reviewInput) {
        String reviewer = reviewInput.reviewer();
        String comment = reviewInput.comment();
        Integer rating = reviewInput.rating();
        String createdAt = Instant.ofEpochSecond(Instant.now().getEpochSecond()).toString();
        return new Review(reviewer, comment, rating, createdAt);
    }
}
