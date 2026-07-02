package com.application.graphql.netflix.service;

import com.application.graphql.netflix.model.dto.BookPageRequest;
import com.application.graphql.netflix.model.dto.PageInfoResult;
import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.model.enums.BookTypeEnum;
import com.application.graphql.netflix.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public PageInfoResult<BookInfo> pageQuery(BookPageRequest request) {
        int page = request.getPageNum() - 1;
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, request.getPageSize(), Sort.by("id").descending());
        Page<BookInfo> pageData = null;
        if (request.getKeyword() == null || request.getKeyword().trim().isEmpty()) {
            pageData = bookRepository.findByAuthorId(request.getAuthorId(), pageable);
        }else {
            pageData = bookRepository.searchBooks(request.getAuthorId(), request.getKeyword(), pageable);
        }
        return PageInfoResult.<BookInfo>builder()
                .pageList(pageData.getContent())
                .totalCount(pageData.getTotalElements())
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .pageCount(pageData.getTotalPages())
                .build();
    }

    @Cacheable(value = "books", key = "#id")
    public Optional<BookInfo> getByBookId(Long id) {
        return bookRepository.findById(id);
    }

    @Cacheable(value = "booksByAuthor", key = "#authorId")
    public List<BookInfo> findByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public List<BookInfo> findByAuthorIds(List<Long> authorIds) {
        return bookRepository.findByAuthorIds(authorIds);
    }

    public Page<BookInfo> findByType(BookTypeEnum type, Pageable pageable) {
        return bookRepository.findByBookType(type, pageable);
    }

    @CacheEvict(value = {"books", "booksByAuthor"}, allEntries = true)
    @Transactional
    public BookInfo save(BookInfo bookInfo) {
        return bookRepository.save(bookInfo);
    }

    public List<BookInfo> findAllById(Set<Long> keys) {
        return bookRepository.findAllById(keys);
    }
}