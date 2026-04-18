package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

class BookRepositoryImpl : BookRepository {

    private val booksList = listOf(
        Book(isbn = "978-0134685991", title = "Clean Code", nbPages = 464),
        Book(isbn = "978-0135957059", title = "The Pragmatic Programmer", nbPages = 352),
        Book(isbn = "978-0201633610", title = "Design Patterns", nbPages = 395),
        Book(isbn = "978-0134757599", title = "Refactoring", nbPages = 448),
        Book(isbn = "978-0596007126", title = "Head First Design Patterns", nbPages = 694),
        Book(isbn = "978-0132350884", title = "Clean Architecture", nbPages = 432),
        Book(isbn = "978-0131103627", title = "The C Programming Language", nbPages = 272),
        Book(isbn = "978-0262033848", title = "Introduction to Algorithms", nbPages = 1312),
        Book(isbn = "978-1491950357", title = "Don't Make Me Think", nbPages = 216),
        Book(isbn = "978-0321125217", title = "Domain-Driven Design", nbPages = 560)

    )
    
    override fun getAllBooks(): List<Book> {
        return booksList
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }
}

