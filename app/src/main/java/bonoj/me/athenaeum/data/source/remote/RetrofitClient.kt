package bonoj.me.athenaeum.data.source.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private lateinit var retrofit: Retrofit

    fun getClient(baseUrl: String): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit
    }
}
