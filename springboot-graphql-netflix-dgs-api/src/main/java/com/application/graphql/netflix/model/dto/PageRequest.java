package com.application.graphql.netflix.model.dto;

import lombok.Data;

@Data
public class PageRequest {
    private int pageNum = 1;
    private int pageSize = 10;
}