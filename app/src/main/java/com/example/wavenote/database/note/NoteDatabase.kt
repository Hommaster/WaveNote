package com.example.wavenote.database.note

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NoteData::class], version = 1, exportSchema = false)
@TypeConverters(NoteDataTypeConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDataDao

}