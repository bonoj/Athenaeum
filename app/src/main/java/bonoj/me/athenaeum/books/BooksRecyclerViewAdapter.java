package bonoj.me.athenaeum.books;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bonoj.me.athenaeum.R;
import bonoj.me.athenaeum.data.Book;


public class BooksRecyclerViewAdapter extends
        RecyclerView.Adapter<BooksRecyclerViewAdapter.ViewHolder>  {

    private List<Book> books;
    private LayoutInflater inflater;
    private Context context;
    private RecyclerViewClickListener clickListener;

    public interface RecyclerViewClickListener {
        void onItemClick(View view, int position);
    }

    // TODO Internal click tracking will probably be removed
    // Track user clicks
    private int clickedPosition = -1;

    public BooksRecyclerViewAdapter(Context context, RecyclerViewClickListener clickListener) {
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
        books = new ArrayList<>();

        Log.i("MVP view", "Constructed BooksRecyclerViewAdapter");
    }

    // Inflates the item layout and returns the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.books_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        context = parent.getContext();

        Log.i("MVP view", "Returning a viewHolder");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BooksRecyclerViewAdapter.ViewHolder holder, int position) {

        //int databaseId = books.get(position).getDatabaseId();
        String title = books.get(position).getTitle();
        String imageUrl = books.get(position).getImageUrl();

        holder.listItemTv.setText(title + " - " + imageUrl);
        //holder.listItemTv.setTag(databaseId);



        // TODO Probably unnecessary to track clicks internally
        if (clickedPosition == position) {
//            Log.i("MVP view", "position " + clickedPosition + " clicked");
//            Intent intent = new Intent(holder.listItemTv.getContext(), DetailsActivity.class);
//            intent.putExtra("ID", databaseId);
//            intent.putExtra("DETAILS", holder.listItemTv.getText().toString());
//            holder.listItemTv.getContext().startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        if (books == null) {
            return 0;
        } else {
            Log.i("MVP view", "getItemCount returned " + books.size());
            return books.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView listItemTv;

        public ViewHolder(View itemView) {
            super(itemView);
            listItemTv = (TextView) itemView.findViewById(R.id.list_item_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }

            clickedPosition = getAdapterPosition();
            notifyItemChanged(clickedPosition);
        }
    }

    // Retrieves the Book at the clicked position
    // TODO Implement parcelable in Book
    public Book getParcelableItem(int position) {
        return books.get(position);
    }

    // Updates the array, allowing for uninterrupted scrolling
    public void setBooks(List<Book> books) {
        if (books == null) {
            books = new ArrayList<>();
            this.books = books;
        } else {
            Log.i("MVP view", "setBooks set " + books.size() + " books");

            this.books.clear();
            this.books.addAll(books);
            notifyDataSetChanged();
        }
    }

    public void resetAdapter() {
        if (books != null) {
            books.clear();
            notifyDataSetChanged();
        }
    }

    // Restores the previous array on device orientation change
    public void refillAdapter(List<Book> oldBooks) {
        if (oldBooks != null) {
            books = new ArrayList<>();
            books.addAll(oldBooks);
        }
    }
}
