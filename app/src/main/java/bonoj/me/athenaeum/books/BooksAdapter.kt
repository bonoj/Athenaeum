package bonoj.me.athenaeum.books

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import bonoj.me.athenaeum.R
import bonoj.me.athenaeum.data.model.Book
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.books_list_item.view.*
import kotlin.properties.Delegates




class BooksAdapter(private val context: Context,
                   private val clickListener: ItemClickListener) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val isPortrait = context.resources.getBoolean(R.bool.isPortrait)

    private var height: Int by Delegates.notNull<Int>()
    private var books: ArrayList<Book> = ArrayList()

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    init {
        setViewHeight()
    }

    fun setViewHeight() {
        val heightRatio: Float
        if (isPortrait) {
            heightRatio = 2.2F
        } else {
            heightRatio = 1.5F
        }
        height = (context.resources.displayMetrics.heightPixels / heightRatio).toInt()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.books_list_item, parent, false)
        val viewHolder = ViewHolder(view)

        view.requestLayout()
        view.layoutParams.height = height
        return viewHolder
    }

    override fun onBindViewHolder(holder: BooksAdapter.ViewHolder, position: Int) {

        val id = books[position].id
        val title = books[position].title
        val imageUrl = books[position].imageUrl

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.placeholder)
        //requestOptions.error(R.drawable.placeholder)

        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                //.placeholder(R.drawable.placeholder)
                .into(holder.listItemIv)

        holder.listItemTv.text = title
        holder.itemView.tag = id
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var listItemTv: TextView = itemView.list_item_tv
        var listItemIv: ImageView = itemView.list_item_iv

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            clickListener.onItemClick(view, adapterPosition)
        }
    }

    fun setBooks(books: List<Book>) {

        for (book in books) {
            if (book !in this.books) {
                this.books.add(book)
            }
        }

        notifyDataSetChanged()
    }

    fun getBooksParcel() : ArrayList<Book> {
        return books
    }

    fun getBook(position: Int): Book {
        return books[position]
    }

    fun refillAdapterAfterDeviceRotation(oldBooks: List<Book>) {
        books.addAll(oldBooks)
    }
}
