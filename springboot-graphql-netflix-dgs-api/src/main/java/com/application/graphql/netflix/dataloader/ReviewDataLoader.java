package com.application.graphql.netflix.dataloader;

import com.application.graphql.netflix.model.entity.ReviewInfo;
import com.application.graphql.netflix.service.ReviewService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsDataLoader(name = "ReviewDataLoader")
public class ReviewDataLoader implements MappedBatchLoader<Long, List<ReviewInfo>> {

    @Autowired
    private ReviewService reviewService;

    @Override
    public CompletableFuture<Map<Long, List<ReviewInfo>>> load(Set<Long> bookIds) {
        return CompletableFuture.supplyAsync(() -> {
            // 批量查询所有 bookId 对应的评论列表
            List<ReviewInfo> reviewInfos = reviewService.findByBookIds(List.copyOf(bookIds));
            // 按 bookId 分组
            return reviewInfos.stream()
                    .collect(Collectors.groupingBy(ReviewInfo::getBookId));
        });
    }
}
