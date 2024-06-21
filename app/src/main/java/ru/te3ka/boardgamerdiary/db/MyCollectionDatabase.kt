package ru.te3ka.boardgamerdiary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.te3ka.boardgamerdiary.dao.MyCollectionDao
import ru.te3ka.boardgamerdiary.db.WantToPlayDatabase.Companion
import ru.te3ka.boardgamerdiary.model.MyCollection

@Database(entities = [MyCollection::class], version = 1, exportSchema = false)
abstract class MyCollectionDatabase : RoomDatabase() {
    abstract fun myCollectionDao(): MyCollectionDao

    companion object {
        @Volatile
        private var INSTANCE: MyCollectionDatabase? = null

        fun getMyCollectionDatabase(context: Context) : MyCollectionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyCollectionDatabase::class.java,
                    "my_collection"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}