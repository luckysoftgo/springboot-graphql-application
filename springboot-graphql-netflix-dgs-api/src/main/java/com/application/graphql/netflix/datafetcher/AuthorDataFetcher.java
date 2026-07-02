package com.application.graphql.netflix.datafetcher;

import com.application.graphql.netflix.model.entity.AuthorInfo;
import com.application.graphql.netflix.model.entity.BookInfo;
import com.application.graphql.netflix.model.input.AuthorInfoInput;
import com.application.graphql.netflix.service.AuthorService;
import com.application.graphql.netflix.service.BookService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@DgsComponent
@RequiredArgsConstructor
public class AuthorDataFetcher {

    private final AuthorService authorService;
    private final BookService bookService;

    @DgsMutation(field = "createAuthor")
    public AuthorInfo createAuthor(@InputArgument AuthorInfoInput authorInput) {
        AuthorInfo authorInfo = AuthorInfo.from(authorInput);
        return authorService.save(authorInfo);
    }

    @DgsQuery(field = "getAuthors")
    public List<AuthorInfo> getAuthors() {
        return authorService.listAll();
    }

    @DgsQuery(field = "authorById")
    public AuthorInfo authorById(@InputArgument Long authorId) {
        return authorService.getAuthorById(authorId).orElse(null);
    }

    /**
     *
     * 字段解析器：为 AuthorInfo 的 bookInfos 提供数据
     *
     * 这个不是直接调用的结果，而是通过 query 中 fetch 的结果
     * 只有 query 中有编写了才会使用，否则是不会被调用的;
     * 也不能在website站点中被的调用
     *
     * 注意：AuthorInfo.bookInfos 中的定义;
     *
     * @param environment
     * @return
     */
    @DgsData(parentType = "AuthorInfo", field = "bookInfos")
    public List<BookInfo> getBookInfos(DgsDataFetchingEnvironment environment) {
        // 获取当前 AuthorInfo 对象（即父对象）
        AuthorInfo author = environment.getSource();
        if (author == null || author.getId() == null) {
            return List.of();
        }
        // 根据作者 ID 查询其所有书籍
        return bookService.findByAuthorId(author.getId());
    }

    /*
    * 在查询单个对象时候，此方式和上述方式效果相同
    * 如果是批量操作，上述方式没有这种方式好
    *
    @DgsData(parentType = "AuthorInfo", field = "bookInfos")
    public CompletableFuture<List<BookInfo>> getBookInfos(DgsDataFetchingEnvironment environment) {
        AuthorInfo author = environment.getSource();
        DataLoader<Long, List<BookInfo>> loader = environment.getDataLoader("AuthorByDataLoader");
        return loader.load(author.getId());
    }
    */

}