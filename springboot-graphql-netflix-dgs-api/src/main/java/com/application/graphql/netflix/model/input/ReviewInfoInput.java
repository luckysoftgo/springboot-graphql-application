package com.application.graphql.netflix.model.input;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfoInput {

    private Long bookId;
    private String reviewer;
    private Integer rating;
    private String reviewComment;
    
}