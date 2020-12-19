object Navigator {
  def main(args: Array[String]): Unit = {
    val source = scala.io.Source.fromResource("input")
    val charting = try new Charting(source.getLines().toList) finally source.close()
    charting.navigate()
  }
}
