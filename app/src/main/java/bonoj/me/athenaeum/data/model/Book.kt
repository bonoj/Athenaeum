package bonoj.me.athenaeum.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.support.annotation.NonNull
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "book_table")
data class Book(
        @PrimaryKey
        @NonNull
        val id: String,
        val title: String,
        val imageUrl: String
) : Parcelable