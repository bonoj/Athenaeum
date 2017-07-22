package bonoj.me.athenaeum.data.model

data class SaleInfo(
    val country: String,
    val saleability: String,
    val isEbook: Boolean,
    val listPrice: ListPrice,
    val retailPrice: RetailPrice,
    val buyLink: String,
    val offers: List<Offer>
)