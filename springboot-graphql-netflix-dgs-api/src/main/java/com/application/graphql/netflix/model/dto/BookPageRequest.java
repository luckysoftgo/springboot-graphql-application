package com.application.graphql.netflix.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookPageRequest extends PageRequest{
    private Long authorId;
    private String keyword;
}