package bonoj.me.athenaeum.data.source.remote

import org.jsoup.Jsoup

object RemoteUtils {

    fun getPublisherString(publisher: String, publishedDate: String) : String {
        return publisher + "\n" + publishedDate
    }

    fun getCleanDescriptionString(description: String) : String {
        return Jsoup.parse(description).text();
    }
}