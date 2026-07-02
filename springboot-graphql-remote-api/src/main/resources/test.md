- **bookInfo-reviewInfo-api**

    1. In a browser, access http://localhost:9080/graphiql

    2. Create a bookInfo and return its id:
       ```graphql
       mutation {
         createBook(bookInput: {title: "Getting Started With Roo", isbn: "9781449307905"}) {
          id
         }
       }
       ```

    3. Add one reviewInfo for the bookInfo created above, suppose the id is `5bd4bd4790e9f641b7388f23`:
       ```graphql
       mutation {
         addBookReview(bookId: "5bd4bd4790e9f641b7388f23", reviewInput: {reviewer: "Ivan Franchin", comment: "It is a very good bookInfo", rating: 5}) {
           id
         }
       }
       ```

    4. Get all books stored in `bookInfo-reviewInfo-api`, including their reviewInfos:
       ```graphql
       {
         getBooks {
           id
           title
           isbn
           reviewInfos {
             comment
             rating
             reviewer
             createdAt
           }
         }
       }
       ```
