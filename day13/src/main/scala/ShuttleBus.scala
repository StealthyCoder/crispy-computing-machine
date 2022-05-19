object ShuttleBus {
  def main(args: Array[String]) : Unit = {
    val source = scala.io.Source.fromResource("input")
    val lines = try source.getLines().toList finally source.close()
    val timetable = new Timetable(lines.head)
    println(timetable.getShuttleBus)
  }
}
