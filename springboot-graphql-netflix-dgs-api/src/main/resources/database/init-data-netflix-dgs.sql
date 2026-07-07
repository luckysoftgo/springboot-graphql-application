-- 创建数据库
CREATE DATABASE IF NOT EXISTS learning-practice-application
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE learning-practice-application;

-- 作者表
DROP TABLE IF EXISTS author_info;
CREATE TABLE author_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    author_name VARCHAR(255) NOT NULL COMMENT '作者姓名',
    author_email VARCHAR(255) COMMENT '作者邮箱',
    author_desc TEXT COMMENT '作者简介',
    birth_date DATE COMMENT '出生日期',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_name (author_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '作者表';

-- 书籍表
DROP TABLE IF EXISTS book_info;
CREATE TABLE book_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    book_title VARCHAR(255) NOT NULL COMMENT '书名',
    book_isbn VARCHAR(255) NOT NULL UNIQUE COMMENT 'ISBN编号',
    publish_date DATE COMMENT '出版日期',
    book_price DECIMAL(10,2) COMMENT '价格',
    book_type VARCHAR(255) NOT NULL COMMENT '书籍类型',
    author_id BIGINT NOT NULL COMMENT '作者ID',
    page_count INT COMMENT '共计多少页',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES author_info(id) ON DELETE CASCADE,
    INDEX idx_author_id (author_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书籍表';

-- 书评表
DROP TABLE IF EXISTS review_info;
CREATE TABLE review_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL COMMENT '书的ID',
    reviewer VARCHAR(100) NOT NULL COMMENT '书评人',
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5) COMMENT '书评分',
    review_comment TEXT COMMENT '书评内容',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '书评时间',
    INDEX idx_book_id (book_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书评表';

-- 插入示例数据
INSERT INTO author_info (author_name, author_email, author_desc, birth_date) VALUES
('J.R.R. Tolkien', 'tolkien@example.com', 'English writer, poet, and university professor', '1892-01-03'),
('George R.R. Martin', 'martin@example.com', 'American novelist and screenwriter', '1948-09-20');

INSERT INTO book_info (book_title, book_isbn, author_id, book_price, publish_date, book_type, page_count) VALUES
('The Lord of the Rings', '978-0544003415', 1, 29.99, '1954-07-29', 'Fantasy', 1178),
('The Hobbit', '978-0547928227', 1, 19.99, '1937-09-21', 'Fantasy', 310),
('A Game of Thrones', '978-0553593716', 2, 24.99, '1996-08-01', 'Fantasy', 694);

INSERT INTO review_info (book_id, reviewer, rating, review_comment) VALUES
(1, 'Alice', 5, 'A masterpiece of fantasy literature'),
(1, 'Bob', 4, 'Long but rewarding read'),
(2, 'Charlie', 5, 'A wonderful introduction to Middle-earth'),
(3, 'Alice', 5, 'Epic storytelling at its finest');