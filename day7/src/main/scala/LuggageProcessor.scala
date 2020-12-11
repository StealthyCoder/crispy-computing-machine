import scala.collection.mutable.ListBuffer

class LuggageProcessor(luggages: List[String]) {
  private var luggage_rules: Map[String, ListBuffer[String]] = Map.empty
  private var containers = 0

  def numberOfBags: Int = containers

  def process(): Unit = {
    luggages.foreach(luggage => {
      val key = "^([\\w\\s]+) bags contain".r.findAllIn(luggage).subgroups.headOption
      if (key.nonEmpty) luggage_rules += (key.get -> ListBuffer.empty)

      val value = "([1-9]+[\\w\\s]+)".r.findAllIn(luggage)
      if (value.hasNext) luggage_rules(key.get).addAll(value.matchData.toList.map(m => m.toString()))
    })
    generateTree()
  }

  def getNumberAndType(str: String): Container = {
    val matches = "([1-9])([\\w\\s]+)".r.findAllIn(str.stripSuffix(" bags").stripSuffix(" bag"))
    Container(matches.group(1).toInt, matches.group(2).trim)
  }

  def getContains(key: String): ListBuffer[String] = luggage_rules(key)

  private def generateTree(): Unit = {
    containers = new Baggage(getNumberAndType("1 shiny gold bag")).value() - 1
  }

}

case class Container(number: Int, color: String)
