package bonoj.me.athenaeum.data.model

data class BooksApiResponse(
        val kind: String,
        val totalItems: Int,
        val items: List<Item>
)