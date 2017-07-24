package bonoj.me.athenaeum.data.source.remote

import android.content.Context
import bonoj.me.athenaeum.R
import org.jsoup.Jsoup

object RemoteUtils {

    fun getAuthorString(authors: List<String>) : String {
        var authorString: String = ""
        var i = 1
        for (author in authors) {
            authorString += author
            if (i < authors.size) {
                authorString += "\n"
            }
            i++
        }
        return authorString
    }

    fun getPublisherString(context: Context, publisher: String, publishedDate: String) : String {

        if (publisher.isEmpty()) {
            return context.getString(R.string.unavailable)
        } else {
            return publisher + "\n" + publishedDate
        }
    }

    fun getCleanDescriptionString(description: String) : String {
        return Jsoup.parse(description).text();
    }

    fun getPageString(context: Context, pageCount: Int) : String {
        var pageString = ""

        if (pageCount > 0) {
            pageString = pageCount.toString() + " " + context.getString(R.string.pages)
        }
        return pageString
    }

    fun getCategoriesString(categories: List<String>) : String {
        var categoriesString = categories.toString()

        if (categoriesString.length >= 2) {
            categoriesString = categoriesString.substring(1, categoriesString.length - 1)
        }
        return categoriesString
    }

    fun getRatingsString(context: Context,
                         averageRating: Double,
                         ratingsCount: Int) : String {

        var ratingsString = ""

        var readers = context.getString(R.string.readers)
        if (ratingsCount == 1) {
            readers = context.getString(R.string.reader)
        }

        if (averageRating > 0.0 && ratingsCount > 0) {
            ratingsString =
                    context.getString(R.string.rated) + " " +
                    averageRating + " " +
                    context.getString(R.string.by) + " " +
                    ratingsCount + " " + readers
        }

        return ratingsString
    }
}