class Navigation(map: List[String], stepSize: Int, slopeSize: Int = 1) {
  private var x, maxX, trees = 0


  def navigate(): Unit = {
    maxX = map.head.length

    for( i <- slopeSize until map.size by slopeSize) {
      if (map(i).charAt(getX) == '#') {
        trees += 1
      }
    }

  }

  private def getX: Int = {
    x+=stepSize
    if ( x > (maxX - 1)) {
      val wrappedStep = x - maxX
      x = 0 + wrappedStep
    }
    x
  }

  def getTrees: Int = {
    trees
  }
}
