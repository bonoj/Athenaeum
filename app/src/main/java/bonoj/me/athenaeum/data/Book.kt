package bonoj.me.athenaeum.data

import io.mironov.smuggler.AutoParcelable

data class Book(
        val id: String,
        val title: String,
        val imageUrl: String
) : AutoParcelable

// Ignore error
// Parcelable implementation handled by Smuggler, no need to provide a CREATOR field.