package com.application.graphql.netflix.service;

import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.model.entity.ReviewInfo;
import com.application.graphql.netflix.model.input.ReviewInfoInput;
import com.application.graphql.netflix.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewInfo createReview(ReviewInfoInput input) {
        ReviewInfo review = ReviewInfo.from(input);
        ReviewInfo saved = reviewRepository.save(review);
        // 重新查询以加载关联的 BookInfo
        return reviewRepository.findByIdWithBook(saved.getId()).orElse(saved);
    }

    public Optional<ReviewInfo> getByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    public List<ReviewInfo> findByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    public List<ReviewInfo> findByBookIds(List<Long> bookIds) {
        return reviewRepository.findByBookIds(bookIds);
    }

}