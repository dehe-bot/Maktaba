package com.ElOuedUniv.maktaba.data.repository

import android.content.Context
import android.net.Uri
import com.ElOuedUniv.maktaba.data.model.Book
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class SupabaseBookRepositoryImpl @Inject constructor(
    private val client: SupabaseClient,
    @ApplicationContext private val context: Context
) : BookRepository {

    override fun getAllBooks(): Flow<List<Book>> = flow {
        val books = client.from("books").select().decodeList<Book>()
        emit(books)
    }

    override fun getBookByIsbn(isbn: String): Book? = null

    suspend fun getBookByIsbnSuspend(isbn: String): Book? {
        return try {
            client.from("books")
                .select {
                    filter {
                        eq("isbn", isbn)
                    }
                }
                .decodeSingleOrNull<Book>()
        } catch (e: Exception) {
            null
        }
    }

    override fun addBook(book: Book) {}

    suspend fun addBookSuspend(book: Book, imageUri: Uri? = null): Book {
        var imageUrl: String? = null

        if (imageUri != null) {
            imageUrl = uploadImage(imageUri)
        }

        val bookToInsert = book.copy(image_url = imageUrl)
        client.from("books").insert(bookToInsert)
        return bookToInsert
    }

    private suspend fun uploadImage(uri: Uri): String? {
        return try {
            val bytes = context.contentResolver.openInputStream(uri)?.readBytes() ?: return null
            val fileName = "covers/${UUID.randomUUID()}.jpg"
            val bucket = client.storage.from("book-covers")
            bucket.upload(fileName, bytes)
            bucket.publicUrl(fileName)
        } catch (e: Exception) {
            null
        }
    }
}
