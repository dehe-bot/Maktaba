package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {
    // 1. تعريف القائمة لي نخزنو فيها الكتب مؤقتاً
    private val booksList = mutableListOf<Book>()

    // 2. تعريف الـ Flow لي يبعث التحديثات للواجهة
    private val _booksFlow = MutableSharedFlow<List<Book>>(replay = 1)
    private val _booksList = listOf(
        Book(isbn = "11111", title = "Clean Code", nbPages = 10),
        Book(isbn = "22222", title = "The Pragmatic Programmer", nbPages = 0),
        Book(isbn = "33333", title = "Design Patterns", nbPages = 0),
        Book(isbn = "44444", title = "Refactoring", nbPages = 0),
        Book(isbn = "55555", title = "Head First Design Patterns", nbPages = 0)
    )

    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList)
    }
    
    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000) // Simulate delay
        emitAll(booksFlow)
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override  fun addBook(book: Book) {
            // 1. نزيدو الكتاب للقائمة المحلية (لي تكون معرفة الفوق في الملف)
            booksList.add(book)

            // 2. نخبرو الـ Flow باللي كاين تحديث (لازم تبعثي القائمة كاملة من جديد)
        _booksFlow.tryEmit(booksList.toList())
            }

        // TODO: Exercise 2 - Implement adding a book to the list and emitting the new list
        // Hint: This is a bit tricky with sharedFlow, think about how to update it.
    }


