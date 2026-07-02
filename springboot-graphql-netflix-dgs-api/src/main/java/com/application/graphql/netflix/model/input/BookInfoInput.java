package com.application.graphql.netflix.model.input;

import com.application.graphql.netflix.model.enums.BookTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoInput {

    private String bookTitle;
    private String bookIsbn;
    private String publishDate;
    private String bookPrice;
    private BookTypeEnum bookType;
    private Integer pageCount;
    private Long authorId;

}