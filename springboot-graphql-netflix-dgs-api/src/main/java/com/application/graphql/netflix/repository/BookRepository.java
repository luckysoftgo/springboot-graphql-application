package com.application.graphql.netflix.repository;

import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.model.enums.BookTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookInfo, Long> {

    List<BookInfo> findByAuthorId(Long authorId);

    List<BookInfo> findAllByAuthorIdIn(List<Long> authorIds);

    Page<BookInfo> findByAuthorId(Long authorId, Pageable pageable);

    @Query(" SELECT b FROM BookInfo b WHERE b.authorId =:authorId AND b.bookTitle LIKE %:keyword% ")
    Page<BookInfo> searchBooks(@Param("authorId") Long authorId,  @Param("keyword") String keyword,Pageable pageable);

    Page<BookInfo> findByBookType(BookTypeEnum bookType, Pageable pageable);

    @Query("SELECT b FROM BookInfo b WHERE b.authorId IN :authorIds")
    List<BookInfo> findByAuthorIds(@Param("authorIds") List<Long> authorIds);
}
