- **netflix-dgs-api**

    1. In a browser, access http://localhost:8888/graphiql

    2. Regarding AuthorInfo operations : 
       ```graphql
       mutation {
         createAuthor(authorInput: {authorName: "狄仁杰",authorEmail:"test@126.com",authorDesc: "狄仁杰自传1",birthDate:"1235-08-09"}) {
            id
            authorName
            authorDesc
         }
       }
       ```
       ```graphql
       {
         authorById(authorId: 3) {
           id
           authorName
           authorEmail
           authorDesc
           birthDate
           createTime
           updateTime            
         }
       }
       ```
       ```graphql
       {
         authorById(authorId: 3) {
           id
           authorName
           authorEmail
           authorDesc
           birthDate
           createTime
           updateTime
           bookInfos {
              id
              bookTitle
              bookIsbn
              bookType
              pageCount
          }              
         }
       }
       ```
       ```graphql
       {
         getAuthors{
           id
           authorName
           authorEmail
           authorDesc
           birthDate
           createTime
           updateTime    
         }
       }
       ```
       
       3. Regarding BookInfo operations : 
          ```graphql
          mutation {
            createBook(bookInput: {bookTitle: "狄仁杰的救赎之道",bookIsbn:"Wessts1235997609",publishDate: "2022-09-01",bookPrice:"36",bookType:FICTION,pageCount: 120,authorId:3}) {
               id
               bookTitle
               bookIsbn
               publishDate
               bookPrice
            }
          }
          ```
          ```graphql
          {
            bookById(bookId: 6) {
               id
               bookTitle
               bookIsbn
               publishDate
               bookPrice
            }
          }
          ```
          ```graphql
          {
            bookById(bookId: 6) {
                id
                bookTitle
                bookIsbn
                publishDate
                bookPrice
                authorInfo{
                  id
                  authorName
                  authorEmail
                  authorDesc
                  birthDate
                  createTime
                  updateTime  
                }
                reviewInfos{
                    id
                    bookId      
                    reviewer
                    rating
                    reviewComment
                }     
            }
          }
          ```
          ```graphql
          {
            getBooks(bookType: FICTION,pageNum:0,pageSize:10) {
               id
               bookTitle
               bookIsbn
               publishDate
               bookPrice
            }
          }
          ```
          ```graphql
          {
            bookPage(pageReq: {authorId:3,keyword:"救赎之道"}) {
               totalCount
               pageNum
               pageSize
               pageCount
               pageList{
                   id
                   bookTitle
                   bookIsbn
                   publishDate
                   bookPrice
               }    
            }
          }
          ```
    4. Regarding ReviewInfo operations :
       ```graphql
           mutation {
             createReview(reviewInput: {bookId: "6",reviewer:"zhangsan",rating: 3,reviewComment:"这本书写的不错，很有见地！我很喜欢"}) {
                id
                bookId      
                reviewer
                rating
                reviewComment
             }
           }
       ```
          ```graphql
          {
            reviewById(reviewId: 5) {
                id
                bookId      
                reviewer
                rating
                reviewComment
                bookInfo{
                    id
                    bookTitle
                    bookIsbn
                    publishDate
                    bookPrice
                }     
            }
          }
          ```
       ```graphql
       {
         getReviews(bookId: 6) {
                id
                bookId      
                reviewer
                rating
                reviewComment
         }
       }
       ```
