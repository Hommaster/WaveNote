package com.example.wavenote.database.repository

import android.content.Context
import androidx.room.Room
import com.example.wavenote.database.categories.Categories
import com.example.wavenote.database.categories.CategoriesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoriesRepository @OptIn(DelicateCoroutinesApi::class)
private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){

    private val database: CategoriesDatabase = Room.databaseBuilder(
        context.applicationContext,
        CategoriesDatabase::class.java,
        "categories_data1"
    ).build()

    fun getCategories(): Flow<List<Categories>> = database.categoriesDao().getAllCategories()

    suspend fun updateCategory(categories: Categories) {
        coroutineScope.launch {
            database.categoriesDao().updateCategory(categories)
        }
    }

    suspend fun addCategory(categories: Categories) = database.categoriesDao().addCategory(categories)

    suspend fun deleteCategory(categories: Categories) = database.categoriesDao().deleteCategory(categories)

    companion object {
        private var INSTANCE: CategoriesRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CategoriesRepository(context)
            }
        }

        fun get(): CategoriesRepository {
            return INSTANCE ?:
            throw IllegalStateException("NoteRepository must be initialized")
        }
    }

}