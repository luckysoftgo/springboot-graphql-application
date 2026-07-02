package com.application.graphql.netflix.datafetcher;

import com.application.graphql.netflix.model.dto.BookPageRequest;
import com.application.graphql.netflix.model.dto.PageRequest;
import com.application.graphql.netflix.model.dto.PageInfoResult;
import com.application.graphql.netflix.model.entity.AuthorInfo;
import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.model.entity.ReviewInfo;
import com.application.graphql.netflix.model.enums.BookTypeEnum;
import com.application.graphql.netflix.model.input.BookInfoInput;
import com.application.graphql.netflix.publisher.BookEventPublisher;
import com.application.graphql.netflix.service.AuthorService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@RequiredArgsConstructor
public class BookDataFetcher {

    private final BookService bookService;
    private final ReviewService reviewService;
    private final AuthorService authorService;
    private final BookEventPublisher bookEventPublisher;

    @DgsMutation(field = "createBook")
    public BookInfo createBook(@InputArgument BookInfoInput bookInput) {
        BookInfo bookInfo = BookInfo.from(bookInput);
        BookInfo result = bookService.save(bookInfo);
        // 发布订阅事件，前端websocket收到bookAdded推送
        bookEventPublisher.publishBookAdded(result);
        return result;
    }

    @DgsQuery(field = "bookById")
    public BookInfo bookById(@InputArgument Long bookId) {
        return bookService.getByBookId(bookId).orElse(null);
    }

    @DgsQuery(field = "getBooks")
    public List<BookInfo> getBooks(@InputArgument String bookType,@InputArgument Integer pageNum,@InputArgument Integer pageSize) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageNum != null ? pageNum : 0,pageSize != null ? pageSize : 10);
        if (bookType != null) {
            Page<BookInfo> bookPage = bookService.findByType(BookTypeEnum.valueOf(bookType), pageable);
            return bookPage.getContent();
        } else {
            return bookService.findByAuthorIds(List.of());
        }
    }

    @DgsQuery(field = "bookPage")
    public PageInfoResult<BookInfo> bookPage(@InputArgument BookPageRequest pageReq) {
        return bookService.pageQuery(pageReq);
    }

    /**
     *
     * 字段解析器：为 BookInfo 的 authorInfo 提供数据
     *
     * 这个不是直接调用的结果，而是通过 query 中 fetch 的结果
     * 只有 query 中有编写了才会使用，否则是不会被调用的;
     * 也不能在 website 站点中被的调用
     *
     * 注意：BookInfo.authorInfo 中的定义;
     *
     * @param environment
     * @return
     */
    @DgsData(parentType = "BookInfo", field = "authorInfo")
    public AuthorInfo getAuthorInfo(DgsDataFetchingEnvironment environment) {
        // 获取当前 BookInfo 对象（即父对象）
        BookInfo bookInfo = environment.getSource();
        if (bookInfo == null || bookInfo.getId() == null) {
            return null;
        }
        // 根据作者 ID 查询其所有书籍
        return authorService.getAuthorById(bookInfo.getAuthorId()).orElse(null);
    }

    /*
    * 在查询单个对象时候，此方式和上述方式效果相同
    * 如果是批量操作，上述方式没有这种方式好
    *
    @DgsData(parentType = "BookInfo", field = "authorInfo")
    public CompletableFuture<AuthorInfo> getAuthorInfo(DgsDataFetchingEnvironment environment) {
        BookInfo book = environment.getSource();
        DataLoader<Long, AuthorInfo> loader = environment.getDataLoader("AuthorDataLoader");
        return loader.load(book.getAuthorId());
    }
     */

    /**
     *
     * 字段解析器：为 BookInfo 的 reviewInfos 提供数据
     *
     * 这个不是直接调用的结果，而是通过 query 中 fetch 的结果
     * 只有 query 中有编写了才会使用，否则是不会被调用的;
     * 也不能在 website 站点中被的调用
     *
     * 注意：BookInfo.reviewInfos 中的定义;
     *
     * @param environment
     * @return
     */
    @DgsData(parentType = "BookInfo", field = "reviewInfos")
    public List<ReviewInfo> getReviewInfos(DgsDataFetchingEnvironment environment) {
        // 获取当前 BookInfo 对象（即父对象）
        BookInfo bookInfo = environment.getSource();
        if (bookInfo == null || bookInfo.getId() == null) {
            return List.of();
        }
        // 根据作者 ID 查询其所有书籍
        return reviewService.findByBookId(bookInfo.getId());
    }

    /*
    * 在查询单个对象时候，此方式和上述方式效果相同
    * 如果是批量操作，上述方式没有这种方式好
    *
    @DgsData(parentType = "BookInfo", field = "reviewInfos")
    public CompletableFuture<List<ReviewInfo>> getReviewInfos(DgsDataFetchingEnvironment environment) {
        BookInfo book = environment.getSource();
        DataLoader<Long, List<ReviewInfo>> loader = environment.getDataLoader("ReviewDataLoader");
        return loader.load(book.getId());
    }
    */
}