package bonoj.me.athenaeum.data

import bonoj.me.athenaeum.data.model.ImageLinks
import io.mironov.smuggler.AutoParcelable

data class Book(
        val id: String,
        val title: String,
        val imageUrl: String
) : AutoParcelable {
    constructor(id: String,
                title: String,
                authorString: String,
                publisherString: String,
                pageString: String,
                categoriesString: String,
                ratingsString: String,
                description: String,
                imageLinks: ImageLinks,
                previewLink: String) : this(id, title, imageLinks.thumbnail)
}
// Parcelable implementation handled by Smuggler, no need to provide CREATOR field