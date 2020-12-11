object Luggage {
  /*
  Regex for getting the first part that will be the key
  ^([\w\s]+) bags contain

  Regex for getting the bottom bags that contain nothing
  no other bags

  Regex for getting the bags that are contained within
  [1-9]+([\w\s]+)
   */
  var processor: Option[LuggageProcessor] = None

  def main(args: Array[String]) : Unit = {
    val source = scala.io.Source.fromResource("input")
    processor = try Option(new LuggageProcessor(source.getLines().toList)) finally source.close()
    processor.get.process()
    println(processor.get.numberOfBags)
  }
}
