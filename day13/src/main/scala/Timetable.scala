import scala.sys.exit
import util.control.Breaks._

class Timetable(timetable: String) {
  val shuttles: Array[(Long, Long)] = timetable
    .split(",")
    .zipWithIndex
    .filter(s => !s._1.equalsIgnoreCase("x"))
    .map(s => (s._1.toLong, s._2.toLong))
    .sortBy(s => s._2)

  def getShuttleBus: Long = {
    var stepSize = shuttles.head._1
    var time = 0L
    shuttles.drop(1).foreach(f => {
      while ((time + f._2) % f._1 != 0L) {
        time += stepSize
      }
      stepSize *= f._1
    })
    return time
  }
}
