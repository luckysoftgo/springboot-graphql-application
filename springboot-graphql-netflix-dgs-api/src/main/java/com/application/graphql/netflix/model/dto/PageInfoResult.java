package com.application.graphql.netflix.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoResult<T> {
    private Integer pageNum;
    private Integer pageSize;
    private List<T> pageList;
    private Long totalCount;
    private Integer pageCount;
}