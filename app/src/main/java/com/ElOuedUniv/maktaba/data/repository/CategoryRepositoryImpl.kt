package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {

    // 1. تعريف قائمة البيانات في مكان واحد صحيح
    private val _categoriesList = listOf(
        Category(
            id = "1",
            name = "Programming",
            description = "Books about software development and coding",
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/drawable/img_programming"
        ),
        Category(
            id = "2",
            name = "Algorithms",
            description = "Books about algorithms and data structures",
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/drawable/img_algorithms"
        ),
        Category(
            id = "3",
            name = "Databases",
            description = "Books about database design and management",
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/drawable/img_databases"
        ),
        Category(
            id = "4",
            name = "Mobile Development",
            description = "Books about Android and mobile app development",
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/drawable/img_mobile"
        ),
        Category(
            id = "5",
            name = "Artificial Intelligence",
            description = "Books about AI, machine learning, and data science",
            imageUrl = "android.resource://com.ElOuedUniv.maktaba/drawable/img_ai"
        )
    )

    // 2. دالة جلب جميع التصنيفات (تأكدي من إغلاق القوس في النهاية)
    override fun getAllCategories(): Flow<List<Category>> = flow {
        emit(_categoriesList)
    }

    // 3. دالة جلب تصنيف معين بواسطة ID
    override fun getCategoryById(id: String): Category? {
        return _categoriesList.find { it.id == id }
    }
}