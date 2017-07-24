package bonoj.me.athenaeum.data

import bonoj.me.athenaeum.data.model.ImageLinks
import bonoj.me.athenaeum.data.model.IndustryIdentifier
import io.mironov.smuggler.AutoParcelable

class BookDetails(
        val title: String,
        val authors: List<String>,
        val publisher: String,
        val publishedDate: String,
        val description: String,
        val industryIdentifiers: List<IndustryIdentifier>,
        val pageCount: Int,
        val printType: String,
        val categories: List<String>,
        val averageRating: Double,
        val ratingsCount: Int,
        val maturityRating: String,
        val imageLinks: ImageLinks,
        val language: String,
        val previewLink: String
)
