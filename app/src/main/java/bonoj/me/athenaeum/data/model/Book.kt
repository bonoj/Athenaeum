package bonoj.me.athenaeum.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
        val id: String,
        val title: String,
        val imageUrl: String
) : Parcelable