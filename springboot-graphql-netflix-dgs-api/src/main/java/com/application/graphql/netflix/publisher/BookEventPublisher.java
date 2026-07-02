package com.application.graphql.netflix.publisher;

import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.model.entity.ReviewInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * 全局事件发布器
 */
@Component
public class BookEventPublisher {

    // 新书事件广播流
    private final Sinks.Many<BookInfo> bookSink = Sinks.many().multicast().onBackpressureBuffer();
    public Flux<BookInfo> bookFlux = bookSink.asFlux();

    // 评论事件广播流
    private final Sinks.Many<ReviewInfo> reviewSink = Sinks.many().multicast().onBackpressureBuffer();
    public Flux<ReviewInfo> reviewFlux = reviewSink.asFlux();


    // Mutation新增书籍时调用，推送事件
    public void publishBookAdded(BookInfo book) {
        bookSink.tryEmitNext(book);
    }

    // Mutation新增评论时调用，推送事件
    public void publishReviewAdded(ReviewInfo review) {
        reviewSink.tryEmitNext(review);
    }
}