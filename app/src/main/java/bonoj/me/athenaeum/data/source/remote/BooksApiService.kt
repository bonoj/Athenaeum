package bonoj.me.athenaeum.data.source.remote

import bonoj.me.athenaeum.data.model.BooksApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface BooksApiService {

    @get:GET("volumes?q=9781451648546&startIndex=0&maxResults=40")
    //fun updateStartIndex(@Query("startIndex") startIndex: Int)
    val books: Call<BooksApiResponse>
}
