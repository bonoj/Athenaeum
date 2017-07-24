package bonoj.me.athenaeum.details

import bonoj.me.athenaeum.data.BookDetails

interface DetailsContract {

    interface View {

        val id: String

        fun displayDetails(bookDetails: BookDetails)

        fun displayError()
    }

    interface Presenter {

        fun loadDetails()

        fun unsubscribe()
    }
}
