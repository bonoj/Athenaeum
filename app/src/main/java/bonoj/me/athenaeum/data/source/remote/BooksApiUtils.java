package bonoj.me.athenaeum.data.source.remote;

import android.util.Log;

public class BooksApiUtils {

    public static final String BASE_URL = "https://www.googleapis.com/books/v1/";

    public static BooksApiService getApiService() {
        Log.i("Retrofitters", "returning a retrofit client and creating a booksApiService");
        return RetrofitClient.getClient(BASE_URL).create(BooksApiService.class);
    }
}
