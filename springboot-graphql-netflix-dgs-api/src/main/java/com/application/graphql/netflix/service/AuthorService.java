package com.application.graphql.netflix.service;

import com.application.graphql.netflix.model.entity.AuthorInfo;
import com.application.graphql.netflix.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Cacheable(value = "authorCache", key = "#id")
    public Optional<AuthorInfo> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public List<AuthorInfo> listAll() {
        return authorRepository.findAll();
    }

    @Cacheable(value = "allAuthors")
    public List<AuthorInfo> findAll() {
        return authorRepository.findAll();
    }

    @Transactional
    public AuthorInfo save(AuthorInfo authorInfo) {
        return authorRepository.save(authorInfo);
    }
}