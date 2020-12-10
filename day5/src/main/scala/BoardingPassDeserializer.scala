object BoardingPassDeserializer {

  def deserialize(boarding_pass: String): Int = {
    var rows = 0 to 127 toList
    var cols = 0 to 8 toList

    def slice_array(b: Boolean, l: List[Int]): List[Int] = {
      if ( b ) l.splitAt(l.size / 2)._2 else l.splitAt(l.size / 2)._1
    }

    for(c <- boarding_pass) {
      if ( "FB".contains(c) ) {
        rows = slice_array(c.equals('B'), rows)
      }
      if ( "RL".contains(c) ) {
        cols = slice_array(c.equals('R'), cols)
      }
    }
    rows.head * 8 + cols.head

  }
}
