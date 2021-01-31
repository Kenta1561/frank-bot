package frank.api.response

import com.google.gson.annotations.SerializedName
import frank.api.entities.board.BoardElement

class BoardResponse(
    @SerializedName("Departure") val elements: List<BoardElement>
) : Response {

    override fun isValid() = elements.isNotEmpty()

}
