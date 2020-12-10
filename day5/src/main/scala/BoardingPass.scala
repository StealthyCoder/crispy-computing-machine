object BoardingPass {
  def main(args: Array[String]) : Unit = {
    val source = scala.io.Source.fromResource("input")
    val validator = try new BoardingPassValidator(source.getLines().toList) finally source.close()
    println(validator.get_highest_seat_id())
    validator.validate()
  }
}
