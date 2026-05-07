package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient

class BookRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : BookRepository {

    override fun getAllBooks(): Flow<List<Book>> {
        // TODO: replace with real Supabase call
        return flow { emit(emptyList()) }
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return null
    }

    override fun addBook(book: Book) {
        // TODO: insert into Supabase
    }
}