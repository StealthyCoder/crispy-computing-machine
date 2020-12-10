class BoardingPassValidator(boarding_passes: List[String]) {

  def validate(): Unit = {
    boarding_passes
      .map(s => BoardingPassDeserializer.deserialize(s))
      .sorted
      .sliding(2, 1)
      .find(ints => Math.abs(ints.head - ints.last) == 2)
      .foreach(ints => println(ints.head + 1))

  }

  def get_highest_seat_id(): Int = {
    var filtered = boarding_passes
    for (f <- high_filter_row) {
      filtered = if (filtered.exists(f)) filtered.filter(f) else filtered
    }
    for (f <- high_filter_column) {
      filtered = if (filtered.exists(f)) filtered.filter(f) else filtered
    }

    BoardingPassDeserializer.deserialize(filtered.head)
  }

  private def high_filter_row = {
    for (i <- 0 to 6) yield (s: String) => s.charAt(i).equals('B')
  }

  private def high_filter_column = {
    for (i <- 7 to 9) yield (s: String) => s.charAt(i).equals('R')
  }

}
