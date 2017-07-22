package bonoj.me.athenaeum.data.source.remote

import bonoj.me.athenaeum.data.model.BooksApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface BooksApiService {

    @get:GET("volumes?q=9781451648546")
    val books: Call<BooksApiResponse>
}
