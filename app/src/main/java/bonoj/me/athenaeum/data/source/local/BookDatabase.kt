package bonoj.me.athenaeum.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import bonoj.me.athenaeum.data.model.Book

@Database(entities = arrayOf(Book::class), version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase? {
            if (INSTANCE == null) {
                synchronized(BookDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase::class.java, "book_database")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}