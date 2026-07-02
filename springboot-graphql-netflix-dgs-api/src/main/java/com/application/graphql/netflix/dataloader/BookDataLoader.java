package com.application.graphql.netflix.dataloader;

import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.service.BookService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.BatchLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsDataLoader(name = "BookDataLoader")
public class BookDataLoader implements MappedBatchLoader<Long, BookInfo> {

    @Autowired
    private BookService bookService;

    @Override
    public CompletableFuture<Map<Long, BookInfo>> load(Set<Long> keys) {
        return CompletableFuture.supplyAsync(() -> {
            List<BookInfo> books = bookService.findAllById(keys);
            return books.stream()
                    .collect(Collectors.toMap(BookInfo::getId, b -> b));
        });
    }

}
