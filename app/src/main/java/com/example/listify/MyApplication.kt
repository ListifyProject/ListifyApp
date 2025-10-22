package com.example.listify

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UserPreferences.init(this)
        Client.init(this)
    }
}