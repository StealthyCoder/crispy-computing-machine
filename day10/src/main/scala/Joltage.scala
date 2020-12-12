class Joltage(adapters: List[Int]) {
  private var distance_one = 0L
  private var distance_three = 0L
  private val _adapters = (0 +: adapters :+ (adapters.max + 3)).sorted

  def chain(): Unit = {
    _adapters.sliding(2,1).foreach(ints => {
      if ( ints.last - ints.head == 1) distance_one += 1
      if ( ints.last - ints.head == 3) distance_three += 1
    })
    println(distance_one * distance_three)
    println(_adapters)
  }

  // Find line of contiguous numbers
  // Apply following on that slice
  // y.subsets.filter(a => a.nonEmpty && a.min == y.min && a.max == y.max).toList
  // Multiply all sizes of those list with each other
}
