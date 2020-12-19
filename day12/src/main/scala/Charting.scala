class Charting(navigation: List[String]) {
  private var x, y = 0L
  private val waypoint = Waypoint(Vector(10, East), Vector(1, North))

  private val degrees: Map[Int, Direction]  = Map(0 -> North, 90 -> East, 180 -> South, 270 -> West)
  private val handlers: Map[Char, Function[Int, Unit]] = Map(
    'N' -> moveNorth,
    'S' -> moveSouth,
    'E' -> moveEast,
    'W' -> moveWest,
    'L' -> turnLeft,
    'R' -> turnRight,
    'F' -> moveForward
  )

  def navigate(): Unit = {
    for(instruction <- navigation) {
      val parsed = parse(instruction)
      if (handlers.contains(parsed._1)) handlers(parsed._1).apply(parsed._2)
      println(waypoint, x, y, parsed)
    }
    println(Math.abs(x) + Math.abs(y))
  }

  private def parse(instruction: String): (Char, Int) = {
    (instruction.charAt(0),instruction.substring(1).toInt)
  }

  private def moveForward(units: Int): Unit = {
    waypoint.x match {
      case v if List(East, West).contains(v.direction) => x += units * v.distance
      case v if List(South, North).contains(v.direction) => y += units * v.distance
      case _ => throw new IllegalArgumentException("direction should be one of East, West, North, South")
    }
    waypoint.y match {
      case v if List(East, West).contains(v.direction) => x += units * v.distance
      case v if List(South, North).contains(v.direction) => y += units * v.distance
      case _ => throw new IllegalArgumentException("direction should be one of East, West, North, South")
    }
  }

  private def moveNorth(units: Int): Unit = {
    if (List(North, South).contains(waypoint.x.direction)) waypoint.x.distance += units
    if (List(North, South).contains(waypoint.y.direction)) waypoint.y.distance += units
  }

  private def moveEast(units: Int): Unit = {
    if (List(East, West).contains(waypoint.x.direction)) waypoint.x.distance += units
    if (List(East, West).contains(waypoint.y.direction)) waypoint.y.distance += units
  }

  private def moveSouth(units: Int): Unit = {
    if (List(North, South).contains(waypoint.x.direction)) waypoint.x.distance -= units
    if (List(North, South).contains(waypoint.y.direction)) waypoint.y.distance -= units
  }

  private def moveWest(units: Int): Unit = {
    if (List(East, West).contains(waypoint.x.direction)) waypoint.x.distance -= units
    if (List(East, West).contains(waypoint.y.direction)) waypoint.y.distance -= units
  }

  private def turnRight(units: Int): Unit = {
    var newDegrees = (degrees.find(p => p._2 == waypoint.x.direction).get._1 + units) % 360
    var changeSign = shouldChange(waypoint.x.direction, degrees(newDegrees))
    waypoint.x.direction = degrees(newDegrees)
    if (changeSign) waypoint.x.distance *= -1

    newDegrees = (degrees.find(p => p._2 == waypoint.y.direction).get._1 + units) % 360
    changeSign = shouldChange(waypoint.y.direction, degrees(newDegrees))
    waypoint.y.direction = degrees(newDegrees)
    if (changeSign) waypoint.y.distance *= -1

  }

  private def turnLeft(units: Int): Unit = {
    turnRight(360 - units)
  }

  private def shouldChange(src: Direction, target: Direction): Boolean = {
    if ( List(East, North).contains(src)) List(West, South).contains(target)
    else List(East, North).contains(target)
  }

}
