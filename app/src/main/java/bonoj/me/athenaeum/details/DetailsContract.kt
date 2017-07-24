package bonoj.me.athenaeum.details

import bonoj.me.athenaeum.data.BookDetails

interface DetailsContract {

    interface View {

        fun displayDetails(bookDetails: BookDetails)

        fun displayNoDetails()

        fun displayError()
    }

    interface Presenter {

        fun loadDetails()

        fun unsubscribe()
    }
}
