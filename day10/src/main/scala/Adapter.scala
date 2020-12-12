object Adapter {
  def main(args: Array[String]) : Unit = {
    val source = scala.io.Source.fromResource("test_input")
    val joltage = try new Joltage(source.getLines().map(s => s.toInt).toList) finally source.close()
    joltage.chain()
  }
}
