package frank.api.response

import com.google.gson.annotations.SerializedName
import frank.api.entities.board.BoardElement

class BoardResponse(@SerializedName("Departures", alternate = ["Arrivals"]) val elements: List<BoardElement>)
