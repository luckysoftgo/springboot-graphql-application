package com.application.graphql.netflix.model.entity;

import com.application.graphql.netflix.model.input.AuthorInfoInput;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "author_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "author_email")
    private String authorEmail;

    @Column(name = "author_desc", columnDefinition = "TEXT")
    private String authorDesc;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    // 一对多：一个作者多本书
    @OneToMany(mappedBy = "authorInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookInfo> bookInfos;

    public static AuthorInfo from(AuthorInfoInput authorInput) {
        return AuthorInfo.builder()
                .authorName(authorInput.getAuthorName())
                .authorEmail(authorInput.getAuthorEmail())
                .authorDesc(authorInput.getAuthorDesc())
                .birthDate(LocalDate.parse(authorInput.getBirthDate()))
                .build();
    }

    public static void updateFrom(AuthorInfoInput authorInput, AuthorInfo author) {
        if (StringUtils.hasLength(authorInput.getAuthorName())) {
            author.setAuthorName(authorInput.getAuthorName());
        }
        if (StringUtils.hasLength(authorInput.getAuthorEmail())) {
            author.setAuthorEmail(authorInput.getAuthorEmail());
        }
        if (StringUtils.hasLength(authorInput.getAuthorDesc())) {
            author.setAuthorDesc(authorInput.getAuthorDesc());
        }
    }

}