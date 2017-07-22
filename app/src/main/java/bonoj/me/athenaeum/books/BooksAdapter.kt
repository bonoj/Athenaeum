package bonoj.me.athenaeum.books

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import bonoj.me.athenaeum.R
import bonoj.me.athenaeum.data.Book
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.books_list_item.view.*


class BooksAdapter(private val context: Context,
                   private val clickListener: ItemClickListener) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val isPortrait = context.resources.getBoolean(R.bool.isPortrait)

    private var height = 0
    private var books: ArrayList<Book> = ArrayList()

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    init {
        setViewHeight()
    }

    fun setViewHeight() {
        var heightRatio: Float
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

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.listItemIv)

        holder.listItemTv.text = title
        holder.listItemTv.tag = id
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

        // TODO Remove if clear is never needed
        //this.books.clear()

        this.books.addAll(books)
        notifyDataSetChanged()
    }

    fun getBook(position: Int): Book {
        return books[position]
    }

    // TODO Rmove if resetAdapter is never needed
    fun resetAdapter() {
        books.clear()
        notifyDataSetChanged()
    }

    // Restores the previous array on device orientation change
    fun refillAdapter(oldBooks: List<Book>) {
        books = ArrayList<Book>()
        books.addAll(oldBooks)
    }
}
