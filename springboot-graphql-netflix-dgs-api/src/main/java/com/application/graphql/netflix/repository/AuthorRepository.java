package com.application.graphql.netflix.repository;

import com.application.graphql.netflix.model.entity.AuthorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorInfo, Long> {

    List<AuthorInfo> findAllByIdIn(List<Long> ids);

}