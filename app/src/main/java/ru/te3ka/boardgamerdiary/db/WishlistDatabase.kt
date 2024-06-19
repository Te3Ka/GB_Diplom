package ru.te3ka.boardgamerdiary.db

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import ru.te3ka.boardgamerdiary.dao.WishlistDao
import ru.te3ka.boardgamerdiary.model.Wishlist

@Database(entities = [Wishlist::class], version = 1, exportSchema = false)
abstract class WishlistDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao

    companion object {
        @Volatile
        private var INSTANCE: WishlistDatabase? = null

        fun getWishlistDatabase(context: Context) : WishlistDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WishlistDatabase::class.java,
                    "wishlist_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}