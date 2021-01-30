package frank.api.entities.board

import com.google.gson.annotations.SerializedName
import frank.api.entities.Product
import frank.util.getDateTime
import frank.util.getNullableDateTime

class BoardElement(
    @SerializedName("Product") val product: Product,
    val direction: String,
    private val date: String,
    private val time: String,
    private val rtDate: String?,
    private val rtTime: String?
) {

    fun getPlannedDateTime() = getDateTime(date, time)

    fun getActualDateTime() = getNullableDateTime(rtDate, rtTime)

}
