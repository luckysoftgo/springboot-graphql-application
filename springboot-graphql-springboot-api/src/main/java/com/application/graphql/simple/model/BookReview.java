package com.application.graphql.simple.model;

import java.util.List;

public record BookReview(String error, String id, List<Review> reviews) {
}
