package com.application.graphql.netflix.datafetcher;

import com.application.graphql.netflix.model.entity.AuthorInfo;
import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.model.entity.ReviewInfo;
import com.application.graphql.netflix.model.input.ReviewInfoInput;
import com.application.graphql.netflix.publisher.BookEventPublisher;
import com.application.graphql.netflix.service.BookService;
import com.application.graphql.netflix.service.ReviewService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@RequiredArgsConstructor
public class ReviewDataFetcher {

    private final ReviewService reviewService;
    private final BookService bookService;
    private final BookEventPublisher bookEventPublisher;

    @DgsMutation(field = "createReview")
    public ReviewInfo createReview(@InputArgument ReviewInfoInput reviewInput) {
        ReviewInfo result = reviewService.createReview(reviewInput);
        // 发布评论事件
        bookEventPublisher.publishReviewAdded(result);
        return result;
    }

    @DgsQuery(field = "getReviews")
    public List<ReviewInfo> getReviews(@InputArgument Long bookId) {
        if (bookId != null) {
            return reviewService.findByBookId(bookId);
        }
        return List.of();
    }

    @DgsQuery(field = "reviewById")
    public ReviewInfo reviewById(@InputArgument Long reviewId) {
        return reviewService.getByReviewId(reviewId).orElse(null);
    }

    /**
     *
     *字段解析器：为 ReviewInfo 的 bookInfo 提供数据
     *
     * 这个不是直接调用的结果，而是通过 query 中 fetch 的结果
     * 只有 query 中有编写了才会使用，否则是不会被调用的;
     * 也不能在 website 站点中被的调用
     *
     * 注意：ReviewInfo.bookInfo 中的定义;
     *
     * @param environment
     * @return
     */
    @DgsData(parentType = "ReviewInfo", field = "bookInfo")
    public BookInfo getReviewInfos(DgsDataFetchingEnvironment environment) {
        // 获取当前 ReviewInfo 对象（即父对象）
        ReviewInfo reviewInfo = environment.getSource();
        if (reviewInfo == null || reviewInfo.getId() == null) {
            return null;
        }
        // 根据作者 ID 查询其所有书籍
        return bookService.getByBookId(reviewInfo.getBookId()).orElse(null);
    }


    /*
    * 在查询单个对象时候，此方式和上述方式效果相同
    * 如果是批量操作，上述方式没有这种方式好

    @DgsData(parentType = "BookInfo", field = "authorInfo")
    public CompletableFuture<BookInfo> getReviewInfos(DgsDataFetchingEnvironment environment) {
        // 获取当前 ReviewInfo 对象（即父对象）
        ReviewInfo reviewInfo = environment.getSource();
        DataLoader<Long, BookInfo> loader = environment.getDataLoader("BookDataLoader");
        return loader.load(reviewInfo.getBookId());
    }
    */

}