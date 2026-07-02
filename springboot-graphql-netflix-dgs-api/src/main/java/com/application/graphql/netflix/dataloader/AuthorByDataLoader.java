package com.application.graphql.netflix.dataloader;

import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.service.BookService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsDataLoader(name = "AuthorByDataLoader")
public class AuthorByDataLoader implements  MappedBatchLoader<Long, List<BookInfo>> {

    @Autowired
    private BookService bookService;

    @Override
    public CompletableFuture<Map<Long, List<BookInfo>>> load(Set<Long> authorIds) {
        return CompletableFuture.supplyAsync(() -> {
            List<BookInfo> books = bookService.findByAuthorIds(List.copyOf(authorIds));
            return books.stream()
                    .collect(Collectors.groupingBy(BookInfo::getAuthorId));
        });
    }

}
