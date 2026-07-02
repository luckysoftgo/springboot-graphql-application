package com.application.graphql.simple.restapi;


import com.application.graphql.simple.client.BookReviewApiClient;
import com.application.graphql.simple.client.BookReviewApiQueryBuilder;
import com.application.graphql.simple.model.Author;
import com.application.graphql.simple.model.Book;
import com.application.graphql.simple.model.BookReview;
import com.application.graphql.simple.restapi.dto.BookResponse;
import com.application.graphql.simple.restapi.dto.CreateBookRequest;
import com.application.graphql.simple.restapi.dto.UpdateBookRequest;
import com.application.graphql.simple.service.AuthorService;
import com.application.graphql.simple.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController("RestApiBookController")
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookReviewApiClient bookReviewApiClient;
    private final BookReviewApiQueryBuilder bookReviewApiQueryBuilder;

    @GetMapping
    public List<BookResponse> getBooks() {
        return bookService.getBooks()
                .stream()
                .map(BookResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{bookId}")
    public BookResponse getBook(@PathVariable Long bookId) {
        Book book = bookService.validateAndGetBookById(bookId);
        return BookResponse.from(book);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookResponse createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        Author author = authorService.validateAndGetAuthorById(createBookRequest.authorId());
        Book book = Book.from(createBookRequest);
        book.setAuthor(author);
        book = bookService.saveBook(book);
        return BookResponse.from(book);
    }

    @PutMapping("/{bookId}")
    public BookResponse updateBook(@PathVariable Long bookId, @Valid @RequestBody UpdateBookRequest updateBookRequest) {
        Book book = bookService.validateAndGetBookById(bookId);
        Book.updateFrom(updateBookRequest, book);
        Long authorId = updateBookRequest.authorId();
        if (authorId != null) {
            Author author = authorService.validateAndGetAuthorById(authorId);
            book.setAuthor(author);
        }
        book = bookService.saveBook(book);
        return BookResponse.from(book);
    }

    @DeleteMapping("/{bookId}")
    public BookResponse deleteBook(@PathVariable Long bookId) {
        Book book = bookService.validateAndGetBookById(bookId);
        bookService.deleteBook(book);
        return BookResponse.from(book);
    }

    @GetMapping("/{bookId}/reviews")
    public BookReview getBookReviews(@PathVariable Long bookId) {
        Book book = bookService.validateAndGetBookById(bookId);
        String graphQLQuery = bookReviewApiQueryBuilder.getBookReviewQuery(book.getIsbn());
        return bookReviewApiClient.getBookReviews(graphQLQuery).toBookReview();
    }
}
