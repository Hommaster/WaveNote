package com.example.wavenote.database.repository

import android.app.Application

class DatabaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
        CategoriesRepository.initialize(this)
    }

}