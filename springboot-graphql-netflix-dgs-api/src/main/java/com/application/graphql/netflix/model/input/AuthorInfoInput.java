package com.application.graphql.netflix.model.input;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorInfoInput {

    private String authorName;
    private String authorEmail;
    private String authorDesc;
    private String birthDate;


}