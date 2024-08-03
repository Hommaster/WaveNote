package com.example.wavenote.database.categories

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Categories::class], version = 1, exportSchema = false)
@TypeConverters(CategoriesTypeConverter::class)
abstract class CategoriesDatabase : RoomDatabase(){

    abstract fun categoriesDao() : CategoriesDao

}