package ru.te3ka.boardgamerdiary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.te3ka.boardgamerdiary.dao.WantToPlayDao
import ru.te3ka.boardgamerdiary.model.WantToPlay

@Database(entities = [WantToPlay::class], version = 1, exportSchema = false)
abstract class WantToPlayDatabase : RoomDatabase() {
    abstract fun wantToPlatDao(): WantToPlayDao

    companion object {
        @Volatile
        private var INSTANCE: WantToPlayDatabase? = null

        fun getWantToPlayDatabase(context: Context) : WantToPlayDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WantToPlayDatabase::class.java,
                    "want_to_play_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}