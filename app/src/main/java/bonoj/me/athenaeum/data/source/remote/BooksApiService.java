package bonoj.me.athenaeum.data.source.remote;

import bonoj.me.athenaeum.data.model.BooksApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BooksApiService {

    @GET("volumes?q=9781451648546")
    Call<BooksApiResponse> getBooks();
}
