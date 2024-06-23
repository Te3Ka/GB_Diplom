package ru.te3ka.boardgamerdiary

import android.app.Application
import ru.te3ka.boardgamerdiary.db.BgdDatabase

class BgdApp : Application() {
    override fun onCreate() {
        super.onCreate()
        BgdDatabase.initializeDatabase(this)
    }
}