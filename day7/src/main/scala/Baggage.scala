class Baggage(container: Container) {
  var children: List[Baggage] = List.empty

  for(baggage <- Luggage.processor.get.getContains(container.color)) {
    children ::= new Baggage(Luggage.processor.get.getNumberAndType(baggage))
  }

  def value(): Int = {
    if ( children.isEmpty ) container.number
    else children.map(b => b.value()).sum * container.number + container.number
  }
}
