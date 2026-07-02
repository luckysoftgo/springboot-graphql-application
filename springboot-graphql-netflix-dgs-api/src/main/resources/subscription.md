- **netflix-dgs-api**

    1. In a browser, access http://localhost:8888/graphiql

       2. Create a user and return the user id:
          ```graphql
            subscription ListenAllNewBooks {
                bookAdded {
                    id
                    bookTitle
                    bookIsbn
                    publishDate
                    bookPrice
                    bookType
                    pageCount
                    authorId
                }
            }
          ```
       
          ```graphql
            subscription ListenBookReview {
                reviewAdded(bookId: 6) {
                    id
                    bookId
                    reviewer
                    rating
                    reviewComment
                }
            }
          ```