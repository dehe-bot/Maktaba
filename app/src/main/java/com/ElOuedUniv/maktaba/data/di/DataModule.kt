package com.ElOuedUniv.maktaba.data.di

import com.ElOuedUniv.maktaba.data.repository.BookRepository
import com.ElOuedUniv.maktaba.data.repository.CategoryRepository
import com.ElOuedUniv.maktaba.data.repository.SupabaseBookRepositoryImpl
import com.ElOuedUniv.maktaba.data.repository.SupabaseCategoryRepositoryImpl
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: SupabaseCategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindBookRepository(
        impl: SupabaseBookRepositoryImpl
    ): BookRepository
}