package bonoj.me.athenaeum.data.source.remote

object BooksApiUtils {

    val BASE_URL = "https://www.googleapis.com/books/v1/"

    val apiService: BooksApiService
        get() = RetrofitClient.getClient(BASE_URL).create(BooksApiService::class.java)
}
