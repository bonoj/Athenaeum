package bonoj.me.athenaeum.data.source.remote

import bonoj.me.athenaeum.data.model.BooksApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BooksApiService {
    
    @GET("volumes?maxResults=40")
    fun setParameters(@Query("startIndex") startIndex: Int,
                      @Query("q") searchString: String,
                      @Query("key") apiKey: String
    ): Call<BooksApiResponse>

    @GET("volumes/{id}")
    fun setId(
              @Path("id") id: String
    ): Call<BooksApiResponse>
}
