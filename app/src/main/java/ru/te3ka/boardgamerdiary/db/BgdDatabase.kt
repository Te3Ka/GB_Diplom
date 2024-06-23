package ru.te3ka.boardgamerdiary.db;

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import kotlin.jvm.Volatile;
import ru.te3ka.boardgamerdiary.dao.*
import ru.te3ka.boardgamerdiary.model.*

@Database(
    entities = [
        Profile::class,
        Contact::class,
        MyCollection::class,
        WantToPlay::class,
        Wishlist::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BgdDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun contactDao(): ContactDao
    abstract fun myCollectionDao(): MyCollectionDao
    abstract fun wantToPlayDao(): WantToPlayDao
    abstract fun wishlistDao(): WishlistDao

    companion object {
        @Volatile
        private var INSTANCE: BgdDatabase? = null

        fun getDatabase(context: Context): BgdDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BgdDatabase::class.java,
                    "bgd_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

        private fun doesDatabaseExist(context: Context, dbName: String): Boolean {
            val dbFile = context.getDatabasePath(dbName)
            return dbFile.exists()
        }

        fun initializeDatabase(context: Context) {
            if (!doesDatabaseExist(context, "bgd_database")) {
                getDatabase(context)
            }
        }
    }
}