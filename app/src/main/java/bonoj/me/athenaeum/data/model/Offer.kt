package bonoj.me.athenaeum.data.model

data class Offer(
    val finskyOfferType: Int,
    val listPrice: ListPrice_,
    val retailPrice: RetailPrice_,
    val giftable: Boolean
)
