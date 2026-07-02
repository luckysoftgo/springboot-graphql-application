package com.application.graphql.netflix.model.entity;

import com.application.graphql.netflix.model.enums.BookTypeEnum;
import com.application.graphql.netflix.model.input.AuthorInfoInput;
import com.application.graphql.netflix.model.input.BookInfoInput;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @Column(name = "book_isbn", unique = true, nullable = false)
    private String bookIsbn;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "book_price")
    private BigDecimal bookPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_type", nullable = false)
    private BookTypeEnum bookType;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private AuthorInfo authorInfo;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    // 一对多：一本书多条评论
    @OneToMany(mappedBy = "bookInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewInfo> reviewInfos;


    public static BookInfo from(BookInfoInput bookInfoInput) {
        return BookInfo.builder()
                .bookTitle(bookInfoInput.getBookTitle())
                .bookIsbn(bookInfoInput.getBookIsbn())
                .publishDate(LocalDate.parse(bookInfoInput.getPublishDate()))
                .bookPrice(new BigDecimal(bookInfoInput.getBookPrice()))
                .bookType(bookInfoInput.getBookType())
                .pageCount(bookInfoInput.getPageCount())
                .authorId(bookInfoInput.getAuthorId())
                .build();
    }

    public static void updateFrom(BookInfoInput bookInfoInput, BookInfo bookInfo) {
        if (StringUtils.hasLength(bookInfoInput.getBookTitle())) {
            bookInfo.setBookTitle(bookInfoInput.getBookTitle());
        }
        if (StringUtils.hasLength(bookInfoInput.getBookPrice())) {
            bookInfo.setBookPrice(new BigDecimal(bookInfoInput.getBookPrice()));
        }
        if (Objects.nonNull(bookInfoInput.getBookType())) {
            bookInfo.setBookType(bookInfoInput.getBookType());
        }
    }

}