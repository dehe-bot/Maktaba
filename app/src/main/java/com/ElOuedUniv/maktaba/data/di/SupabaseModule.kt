package com.ElOuedUniv.maktaba.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://feowqyknlokzbzdcqttd.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZlb3dxeWtubG9remJ6ZGNxdHRkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzc4OTA5NDksImV4cCI6MjA5MzQ2Njk0OX0.VEMXtoAmACIh5FXb7tOi1E52DSLqOf4n0gTZEbMsmG4"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}