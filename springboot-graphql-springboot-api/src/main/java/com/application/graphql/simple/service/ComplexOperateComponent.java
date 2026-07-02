package com.application.graphql.simple.service;

import com.application.graphql.simple.exception.AuthorNotFoundException;
import com.application.graphql.simple.model.Author;
import com.application.graphql.simple.model.Book;
import com.application.graphql.simple.repository.AuthorRepository;
import com.application.graphql.simple.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ComplexOperateComponent {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Transactional(rollbackFor = Exception.class)
    public Author saveAuthor(Author author) {
        if (CollectionUtils.isEmpty(author.getBooks())) {
            return authorRepository.save(author);
        } else {
            author = authorRepository.save(author);
            Author finalAuthor = author;
            author.getBooks().stream().forEach(book -> book.setAuthor(finalAuthor));
            bookRepository.saveAllAndFlush(author.getBooks());
            return author;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveBooks(List<Book> books) {
        if (CollectionUtils.isEmpty(books)) {
            return false;
        }
        for (Book book : books) {
            authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id '%s' not found", book.getAuthor().getId())));
        }
        bookRepository.saveAllAndFlush(books);
        return true;
    }

}
