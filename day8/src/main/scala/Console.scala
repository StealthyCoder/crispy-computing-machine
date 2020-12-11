object Console {
  def main(args: Array[String]) : Unit = {
    val source = scala.io.Source.fromResource("input")
    val analyzer = try new Analyzer(source.getLines().toList) finally source.close()
    analyzer.analyze()
  }
}
