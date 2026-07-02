package com.application.graphql.simple.graphql;

import com.application.graphql.simple.graphql.input.AuthorInput;
import com.application.graphql.simple.model.Author;
import com.application.graphql.simple.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller("GraphQlAuthorController")
public class AuthorController {

    private final AuthorService authorService;

    @QueryMapping
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @QueryMapping
    public Author getAuthorById(@Argument Long authorId) {
        return authorService.validateAndGetAuthorById(authorId);
    }
    @QueryMapping
    public List<Author> getAuthorByIds(@Argument List<Long> authorIds) {
        return authorService.getAuthorByIds(authorIds);
    }

    @QueryMapping
    public List<Author> getAuthorByName(@Argument String authorName) {
        return authorService.validateAndGetAuthorByName(authorName);
    }

    @MutationMapping
    public Author createAuthor(@Argument AuthorInput authorInput) {
        Author author = Author.from(authorInput);
        return authorService.saveAuthor(author);
    }

    @MutationMapping
    public Boolean createAuthors(@Argument List<AuthorInput> authorInputs) {
        return authorService.saveAuthors(authorInputs);
    }

    @MutationMapping
    public Author updateAuthor(@Argument Long authorId, @Argument AuthorInput authorInput) {
        Author author = authorService.validateAndGetAuthorById(authorId);
        Author.updateFrom(authorInput, author);
        return authorService.saveAuthor(author);
    }

    @MutationMapping
    public Author deleteAuthor(@Argument Long authorId) {
        Author author = authorService.validateAndGetAuthorById(authorId);
        authorService.deleteAuthor(author);
        return author;
    }
}
