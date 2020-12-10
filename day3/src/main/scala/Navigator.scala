object Navigator {
  def main(args: Array[String]): Unit = {
    val source = scala.io.Source.fromResource("input")
    val chart = try source.getLines().toList finally source.close()

    var navigation = new Navigation(chart, 3)
    navigation.navigate()
    var trees = navigation.getTrees.toLong

    navigation = new Navigation(chart, 1)
    navigation.navigate()
    trees *= navigation.getTrees

    navigation = new Navigation(chart, 5)
    navigation.navigate()
    trees *= navigation.getTrees

    navigation = new Navigation(chart, 7)
    navigation.navigate()
    trees *= navigation.getTrees

    navigation = new Navigation(chart, 1, 2)
    navigation.navigate()
    println(trees * navigation.getTrees)


  }
}
