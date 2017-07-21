package bonoj.me.athenaeum.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import bonoj.me.athenaeum.R;
import bonoj.me.athenaeum.data.model.GoogleBook;
import bonoj.me.athenaeum.data.model.Item;
import bonoj.me.athenaeum.data.model.VolumeInfo;
import bonoj.me.athenaeum.data.source.remote.BooksApiService;
import bonoj.me.athenaeum.data.source.remote.BooksApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private BooksApiService booksApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        booksApiService = BooksApiUtils.getApiService();

        loadBooksFromApi();
    }

    public void loadBooksFromApi() {

        booksApiService.getBooks().enqueue(new Callback<GoogleBook>() {

            @Override
            public void onResponse(Call<GoogleBook> call, Response<GoogleBook> response) {

                if (response.isSuccessful()) {

                    Log.i("Retrofitters", "books loaded from API");
                    Log.i("Retrofitters", response.toString());

                    GoogleBook googleBook = response.body();
                    List<Item> items = googleBook.getItems();
                    VolumeInfo volumeInfo = items.get(0).getVolumeInfo();
                    String title = volumeInfo.getTitle();

                    Log.i("Retrofitters", title);

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
