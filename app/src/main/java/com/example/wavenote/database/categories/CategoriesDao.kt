package com.example.wavenote.database.categories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.wavenote.database.note.NoteData
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoriesDao {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Categories>>

    @Update
    suspend fun updateCategory(categories: Categories)

    @Insert
    suspend fun addCategory(categories: Categories)

    @Delete
    suspend fun deleteCategory(categories: Categories)

}