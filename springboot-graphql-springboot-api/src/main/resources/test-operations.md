- **authorInfo-bookInfo-api**

    1. In a browser, access http://localhost:8080/graphiql

       2. Create an authorInfo and return the authorInfo id:
          ```graphql
          mutation {
            createAuthor(authorInput: {name: "Josh Long"}) {
              id
            }
          }
          ```
          ```graphql
          mutation {
            createAuthor(authorInput: {name: "Bruce Lee",
                   books: [
                   {authorId: 2,isbn: "8881449307905", title: "How to use SpringBoot framework to build a RESTful API", year: 2020},    
                   {authorId: 2,isbn: "8881449307955", title: "10天掌握Golang 语言", year: 2021}
                   ]
               }) {
              id
            }
          }
          ```
          ```graphql
          mutation {
            success: createAuthors(authorInputs: [
              {name: "稻盛和夫",books: [
                       {authorId: 0,isbn: "1111449307905", title: "活法 卷I", year: 2010},    
                       {authorId: 0,isbn: "1111449307955", title: "活法 卷II", year: 2011}
                       ]},
              {name: "李嘉诚",books: [
                       {authorId: 0,isbn: "2221449307905", title: "我是怎么成为千万富翁的", year: 2006},    
                       {authorId: 0,isbn: "2221449307955", title: "李嘉诚传", year: 2006}
                       ]},
              {name: "马云",books: [
                   {authorId: 0,isbn: "3331449307905", title: "阿里巴巴失败的300个事件", year: 2023},    
                   {authorId: 0,isbn: "3331449307955", title: "马云传", year: 2024}
                   ]}]
              ) 
          }
          ```
       
    3. Create a bookInfo and return the bookInfo id and authorInfo name:
       > **Note**: while creating this bookInfo in `authorInfo-bookInfo-api`, we are setting the same ISBN, `9781449307905`, as we did when creating the bookInfo in `bookInfo-reviewInfo-api`.
       ```graphql
       mutation {
         createBook(bookInput: {authorId: 1, isbn: "9781449307905", title: "Getting Started With Roo", year: 2020}) {
           id
         }
       }
       ```
       ```graphql
       mutation {
         success : createBooks(bookInputs: [{authorId: 3, isbn: "9781449307100", title: "10天掌握Python 语言", year: 2022}, 
                                  {authorId: 3, isbn: "9781449307200", title: "10天掌握C# 语言", year: 2022},
                                  {authorId: 3, isbn: "9781449307300", title: "10天掌握JAVA 语言", year: 2022}]) 
       }
       ```
       
    4. Get authorInfo by id and return some information about his/her books including bookInfo reviewInfos from `bookInfo-reviewInfo-api`:
       > **Note**: as the bookInfo stored in `authorInfo-bookInfo-api` and `bookInfo-reviewInfo-api` has the same ISBN, `9781449307905`, it's possible to retrieve the reviewInfos of the bookInfo. Otherwise, an empty list will be returned in case `bookInfo-reviewInfo-api` does not have a specific ISBN or the service is down.
       ```graphql
       {
         getAuthorById(authorId: 1) {
           name
           books {
             isbn
             title
           }
         }
       }
       ```
       ```graphql
       {
         getAuthorByIds(authorIds: [1, 2, 3]) {
           name
           books {
             isbn
             title
           }
         }
       }
       ```
       ```graphql
       {
         getAuthorByName(authorName: "马云") {
           name
           books {
             isbn
             title
           }
         }
       }
       ```
       ```graphql
       {
         getBookById(bookId: 1) {
           id
           isbn
           title
           year
         }
       }
       ```
       ```graphql
       {
         getBookByIds(bookIds: [1,2,3]) {
           id
           isbn
           title
           year
         }
       }
       ```
    5. Update bookInfo title and return its id and new title:
       ```graphql
       mutation {
         updateBook(bookId: 1, bookInput: {title: "Getting Started With Roo 2"}) {
           id
           title
         }
       }
       ```
    6. Delete the authorInfo and return the authorInfo id:
       ```graphql
       mutation {
         deleteAuthor(authorId: 1) {
           id
         }
       }
       ```
  7. bookInfo reviewInfo api:
     ```graphql
       {
         getBookReview(isbn:"9781449307905") {
           id
           error
           reviewInfos {
             reviewer
             rating
             comment
             createdAt
           }
         }
       }
     ```
