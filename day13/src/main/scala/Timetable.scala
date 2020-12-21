class Timetable(departure: Int, timetable: String) {
  val shuttles: Array[Int] = timetable
    .split(",")
    .filter(s => !s.equalsIgnoreCase("x"))
    .map(s => s.toInt)
  var service : Map[Int, Int] = Map.empty

  def getShuttleBus: Int = {
    for (shuttle <- shuttles ) {
      service += shuttle -> (shuttle - (departure % shuttle))
    }
    val min = service.minBy(kv => kv._2)
    min._1 * min._2
  }
}
