package com.application.graphql.netflix.dataloader;

import com.application.graphql.netflix.model.entity.AuthorInfo;
import com.application.graphql.netflix.service.AuthorService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsDataLoader(name = "AuthorDataLoader")
public class AuthorDataLoader implements MappedBatchLoader<Long, AuthorInfo> {

    @Autowired
    private AuthorService authorService;

    @Override
    public CompletableFuture<Map<Long, AuthorInfo>> load(Set<Long> keys) {
        return CompletableFuture.supplyAsync(() -> {
            List<AuthorInfo> authorInfos = authorService.findAll().stream()
                    .filter(a -> keys.contains(a.getId()))
                    .collect(Collectors.toList());
            return authorInfos.stream().collect(Collectors.toMap(AuthorInfo::getId, a -> a));
        });
    }
}
