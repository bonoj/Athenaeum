package bonoj.me.athenaeum.data

import bonoj.me.athenaeum.data.model.ImageLinks

class BookDetails(
        val title: String,
        val authorString: String,
        val publisherString: String,
        val pageString: String,
        val categoriesString: String,
        val ratingsString: String,
        val description: String,
        val imageLinks: ImageLinks,
        val previewLink: String
)
