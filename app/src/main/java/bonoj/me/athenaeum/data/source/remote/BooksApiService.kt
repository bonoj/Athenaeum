package bonoj.me.athenaeum.data.source.remote

import bonoj.me.athenaeum.data.model.BooksApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface BooksApiService {

    @get:GET("volumes?q=9781451648546&startIndex=0&maxResults=40")
    val books: Call<BooksApiResponse>

    @GET("volumes?q=intitle:apple&maxResults=40")
    fun getStartIndex(@Query("startIndex") startIndex: Int): Call<BooksApiResponse>

//    @GET("volumes?q=9781451648546&maxResults=40")
//    fun getStartIndex(@Query("startIndex") startIndex: Int): Call<BooksApiResponse>
}
