package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SupabaseCategoryRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : CategoryRepository {

    private val defaultCategories = listOf(
        Category(id = "1", name = "Programming", description = "Books about software development and coding"),
        Category(id = "2", name = "Algorithms", description = "Books about algorithms and data structures"),
        Category(id = "3", name = "Databases", description = "Books about database design and management"),
        Category(id = "4", name = "Networking", description = "Books about computer networks and protocols"),
        Category(id = "5", name = "AI & Machine Learning", description = "Books about artificial intelligence and ML"),
        Category(id = "6", name = "Design Patterns", description = "Books about software architecture and patterns"),
    )

    override fun getAllCategories(): Flow<List<Category>> = flow {
        try {
            val categories = client.from("categories").select().decodeList<Category>()
            if (categories.isEmpty()) {
                emit(defaultCategories)
            } else {
                emit(categories)
            }
        } catch (e: Exception) {
            emit(defaultCategories)
        }
    }

    override fun getCategoryById(id: String): Category? = null
}