object Lobby {
  def main(args: Array[String]): Unit = {
    val source = scala.io.Source.fromResource("input")
    try Seating.analyze(source.getLines().toList) finally source.close()
  }
}
