package com.ElOuedUniv.maktaba.data.repository
import com.ElOuedUniv.maktaba.R
import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    private val _booksList = mutableListOf(
        Book(isbn = "9780136083221", title = "Clean Code", nbPages = 431,
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/${R.drawable.book_clean_code}"),
        Book(isbn = "9780135957059", title = "The Pragmatic Programmer", nbPages = 352,
            imageUrl ="android.resource://com.ElOuedUniv.maktaba/ ${R.drawable.book_the_pragmatic}"),
        Book(isbn = "0201633612", title = "Design Patterns", nbPages = 395,
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/${R.drawable.book_design_patterns}"),
        Book(isbn = "9780201485677", title = "Refactoring", nbPages = 464,
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/${R.drawable.book_refactoring}"),
        Book(isbn = "9781492078005", title = "Head First Design Patterns", nbPages = 672,
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/${R.drawable.book_head_first}"),
        Book(isbn = "9780262046305", title = "Introduction to Algorithms Cormen", nbPages = 1312,
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/${R.drawable.book_algorithms}"),
        Book(isbn = "9781934356364", title = "Learn to Program", nbPages = 230,
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/${R.drawable.book_learn_program}"),
        Book(isbn = "9780262518802", title = "Algorithms Unlocked", nbPages = 240,
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/${R.drawable.book_algorithms_unlocked}"),
        Book(isbn = "9780134340012", title = "How to Solve it by Computer", nbPages = 442,
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/${R.drawable.book_how_to_solve}"),
        Book(isbn = "9780735619678", title = "Code Complete", nbPages = 914,
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/${R.drawable.book_code_complete}")
    )


    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList.toList())
    }
    
    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000) // Simulate delay
        emitAll(booksFlow)
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override fun addBook(book: Book) {
        _booksList.add(book)
        booksFlow.tryEmit(_booksList.toList())
    }
}
