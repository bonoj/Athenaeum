package bonoj.me.athenaeum.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import bonoj.me.athenaeum.R;
import bonoj.me.athenaeum.data.GoogleBook;
import bonoj.me.athenaeum.data.source.remote.ApiService;
import bonoj.me.athenaeum.data.source.remote.ApiUtilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        apiService = ApiUtilities.getApiService();

        loadBooksFromApi();
    }

    public void loadBooksFromApi() {
        
        apiService.getBooks().enqueue(new Callback<GoogleBook>() {

            @Override
            public void onResponse(Call<GoogleBook> call, Response<GoogleBook> response) {

                if (response.isSuccessful()) {

                    Log.i("Retrofitters", "books loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<GoogleBook> call, Throwable t) {
                Log.i("Retrofitters", "error loading from API");

            }
        });
    }
}
