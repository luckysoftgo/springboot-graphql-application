package com.application.graphql.netflix.subscription;

import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.model.entity.ReviewInfo;
import com.application.graphql.netflix.publisher.BookEventPublisher;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * 订阅监听入口
 * 对标的是 BookEventPublisher 事件发布.
 */
@DgsComponent
@RequiredArgsConstructor
public class BookSubscriptionFetcher {

    private final BookEventPublisher eventPublisher;

    /**
     * 全局新书订阅
     */
    @DgsSubscription
    public Flux<BookInfo> bookAdded() {
        return eventPublisher.bookFlux;
    }

    /**
     * 按bookId过滤评论，仅推送目标书籍评论
     */
    @DgsSubscription
    public Flux<ReviewInfo> reviewAdded(@InputArgument Long bookId) {
        return eventPublisher.reviewFlux
                .filter(review -> review.getBookInfo().getId().equals(bookId));
    }
}