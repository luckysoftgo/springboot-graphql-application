package com.application.graphql.simple.repository;

import com.application.graphql.simple.model.Author;
import com.application.graphql.simple.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthor(Author author);
}
