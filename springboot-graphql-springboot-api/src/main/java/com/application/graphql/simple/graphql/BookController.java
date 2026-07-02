package com.application.graphql.simple.graphql;

import com.application.graphql.simple.client.BookReviewApiClient;
import com.application.graphql.simple.client.BookReviewApiQueryBuilder;
import com.application.graphql.simple.graphql.input.BookInput;
import com.application.graphql.simple.model.Author;
import com.application.graphql.simple.model.Book;
import com.application.graphql.simple.model.BookReview;
import com.application.graphql.simple.service.AuthorService;
import com.application.graphql.simple.service.BookService;
import com.application.graphql.simple.service.ComplexOperateComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller("GraphQlBookController")
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final BookReviewApiQueryBuilder bookReviewApiQueryBuilder;
    private final BookReviewApiClient bookReviewApiClient;
    private final ComplexOperateComponent operateComponent;

    @QueryMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @QueryMapping
    public Book getBookById(@Argument Long bookId) {
        return bookService.validateAndGetBookById(bookId);
    }

    @QueryMapping
    public List<Book> getBookByIds(@Argument List<Long> bookIds) {
        return bookService.getBookByIds(bookIds);
    }

    @MutationMapping
    public Book createBook(@Argument BookInput bookInput) {
        Author author = authorService.validateAndGetAuthorById(bookInput.authorId());
        Book book = Book.from(bookInput);
        book.setAuthor(author);
        return bookService.saveBook(book);
    }

    @MutationMapping
    public Boolean createBooks(@Argument List<BookInput> bookInputs) {
        return bookService.saveBooks(bookInputs);
    }

    @MutationMapping
    public Book updateBook(@Argument Long bookId, @Argument BookInput bookInput) {
        Book book = bookService.validateAndGetBookById(bookId);
        Book.updateFrom(bookInput, book);

        Long authorId = bookInput.authorId();
        if (authorId != null) {
            Author author = authorService.validateAndGetAuthorById(authorId);
            book.setAuthor(author);
        }
        return bookService.saveBook(book);
    }

    @MutationMapping
    public Book deleteBook(@Argument Long bookId) {
        Book book = bookService.validateAndGetBookById(bookId);
        bookService.deleteBook(book);
        return book;
    }

    @QueryMapping
    public BookReview getBookReview(@Argument String isbn) {
        String graphQLQuery = bookReviewApiQueryBuilder.getBookReviewQuery(isbn);
        return bookReviewApiClient.getBookReviews(graphQLQuery).toBookReview();
    }
}
