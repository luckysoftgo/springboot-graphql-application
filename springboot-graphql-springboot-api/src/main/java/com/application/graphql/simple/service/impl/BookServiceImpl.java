package com.application.graphql.simple.service.impl;


import com.application.graphql.simple.exception.AuthorNotFoundException;
import com.application.graphql.simple.exception.BookDuplicatedIsbnException;
import com.application.graphql.simple.exception.BookNotFoundException;
import com.application.graphql.simple.graphql.input.BookInput;
import com.application.graphql.simple.model.Author;
import com.application.graphql.simple.model.Book;
import com.application.graphql.simple.repository.AuthorRepository;
import com.application.graphql.simple.repository.BookRepository;
import com.application.graphql.simple.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public Book validateAndGetBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(String.format("Book with id '%s' not found", id)));
    }

    @Override
    public List<Book> getBookByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return List.of();
        } else {
            return bookRepository.findAllById(ids);
        }
    }

    @Override
    public Book saveBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new BookDuplicatedIsbnException(String.format("Book with ISBN '%s' already exists", book.getIsbn()));
        }
    }

    @Override
    public boolean saveBooks(List<BookInput> bookInputs) {
        if (CollectionUtils.isEmpty(bookInputs)) {
            return false;
        }
        List<Book> books = new ArrayList<>();
        for (BookInput bookInput : bookInputs) {
            Author author = authorRepository.findById(bookInput.authorId())
                    .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id '%s' not found",bookInput.authorId())));
            Book book = Book.from(bookInput);
            book.setAuthor(author);
            books.add( book);
        }
        try {
            bookRepository.saveAllAndFlush(books);
        } catch (DataIntegrityViolationException e) {
            log.error(String.format("Book with ISBN '%s' already exists", books.get(0).getIsbn()));
            return false;
        }
        return true;
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }
}
