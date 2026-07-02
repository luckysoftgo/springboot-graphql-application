package com.application.graphql.netflix.model.entity;

import com.application.graphql.netflix.model.input.ReviewInfoInput;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "review_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "reviewer", nullable = false)
    private String reviewer;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "review_comment", columnDefinition = "TEXT")
    private String reviewComment;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private BookInfo bookInfo;

    public static ReviewInfo from(ReviewInfoInput reviewInfoInput) {
        return ReviewInfo.builder()
                .bookId(reviewInfoInput.getBookId())
                .reviewer(reviewInfoInput.getReviewer())
                .rating(reviewInfoInput.getRating())
                .reviewComment(reviewInfoInput.getReviewComment())
                .build();
    }
}