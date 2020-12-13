import scala.collection.immutable.TreeSet

class Joltage(adapters: List[Int]) {
  private var distance_one = 0L
  private var distance_three = 0L
  private var _combinations = 1L
  private val _adapters = (0 +: adapters :+ (adapters.max + 3)).sorted

  def chain(): Unit = {
    _adapters.sliding(2, 1).foreach(ints => {
      if (ints.last - ints.head == 1) distance_one += 1
      if (ints.last - ints.head == 3) distance_three += 1
    })
    println(distance_one * distance_three)
    println(_adapters)
  }

  def chainAll(): Long = {
    _adapters
      .sliding(2, 1)
      .filter(pair => pair.last - pair.head == 3)
      .map(pair => _adapters.indexOf(pair.last))
      .toList.+:(0).:+(_adapters.max + 3)
      .sliding(2, 1)
      .foreach(pair => _combinations *= combinations(_adapters.slice(pair.head, pair.last + 1)))
    _combinations
  }

  private def combinations(ints: List[Int]): Long = {
    ints.to(TreeSet)
      .subsets
      .filter(nums => nums.nonEmpty &&
        nums.min == ints.min &&
        nums.max == ints.max)
      .filter(nums => nums
        .sliding(2, 1)
        .forall(pair => pair.last - pair.head <= 3))
      .toList
      .size
  }

}
