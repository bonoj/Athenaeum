package bonoj.me.athenaeum.data.source.remote;

import bonoj.me.athenaeum.data.GoogleBook;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/volumes?q=9781451648546")
    Call<GoogleBook> getBooks();
}
