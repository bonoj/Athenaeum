package bonoj.me.athenaeum.data.source.remote;

public class ApiUtilities {

    public static final String BASE_URL = "https://www.googleapis.com/books/v1/";

    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
