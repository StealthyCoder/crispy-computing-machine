class Seating(rows: Int, cols: Int) {
  private val layout = Array.ofDim[Seat](rows, cols)

  private def layouts_are_equal(): Boolean = {
    var result: List[Boolean] = List.empty
    for (row <- layout.indices) {
      for (col <- layout(row).indices) {
        result :+= ! layout(row)(col).hasChanged
      }
    }
    result.forall(p => p)
  }

  private def countOccupied(): Long = {
    var result = 0L
    for (row <- layout) {
      result += row.count(s => s.getState.equals('#'))
    }
    result
  }
}

  object Seating {
    def analyze(seating: List[String]): Unit = {
      val _seating = new Seating(seating.length, seating.head.length - 1)
      for (row <- seating.indices) {
        var seats = List.empty[Seat]
        for (col <- seating(row).indices) {
          val seat = new Seat(seating(row).charAt(col), col, row)
          seats :+= seat
        }
        _seating.layout.update(row, seats.toArray)
      }
      for (row <- seating.indices) {
        for (col <- seating(row).indices) {
          _seating.layout(row)(col).line_of_sight(_seating.layout)
        }
      }

      var running = true
      var round = 0L
      while (running) {
        for (row <- seating.indices) {
          for (col <- seating(row).indices) {
            _seating.layout(row)(col).queueChange()
          }
        }
        for (row <- seating.indices) {
          for (col <- seating(row).indices) {
            _seating.layout(row)(col).change()
          }
        }

        if (_seating.layouts_are_equal()) running = false
        round += 1
        println("Performed round : " + round)
      }
      println("Occupied Seats: " + _seating.countOccupied())
    }
  }
