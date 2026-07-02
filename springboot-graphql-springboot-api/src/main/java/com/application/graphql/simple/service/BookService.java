package com.application.graphql.simple.service;


import com.application.graphql.simple.graphql.input.BookInput;
import com.application.graphql.simple.model.Author;
import com.application.graphql.simple.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getBooks();

    List<Book> getBooksByAuthor(Author author);

    Book validateAndGetBookById(Long id);

    List<Book> getBookByIds(List<Long> ids);

    Book saveBook(Book book);

    boolean saveBooks(List<BookInput> bookInputs);

    void deleteBook(Book book);
}
