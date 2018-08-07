package bonoj.me.athenaeum.data.source

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import bonoj.me.athenaeum.data.model.Book

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: Book)

    @Query("DELETE FROM book_table")
    fun deleteBooks()

    @Query("SELECT * from book_table")
    fun getBooks() : List<Book>
}