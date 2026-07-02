package com.application.graphql.netflix.repository;

import com.application.graphql.netflix.model.entity.ReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewInfo, Long> {
    List<ReviewInfo> findByBookId(Long bookId);

    List<ReviewInfo> findAllByBookIdIn(List<Long> bookIds);

    @Query("SELECT r FROM ReviewInfo r WHERE r.bookId IN :bookIds")
    List<ReviewInfo> findByBookIds(@Param("bookIds") List<Long> bookIds);

    @Query("SELECT r FROM ReviewInfo r LEFT JOIN FETCH r.bookInfo WHERE r.id = :id")
    Optional<ReviewInfo> findByIdWithBook(@Param("id") Long id);
}