object Expenses {
  def main(args: Array[String]) : Unit = {
    val source = scala.io.Source.fromResource("input")
    val expenses = try source.getLines().map(s => s.toInt).toList finally source.close()
    expenses.foreach(expense => {
      val counterPart = 2020 - expense
      expenses.find(p => p == counterPart).map(p => counterPart * expense).map(println)
    })
    expenses.foreach(expense => {
      val counterPart = 2020 - expense
      expenses.foreach(other => {
        val thirdPart = counterPart - other
        if ( expenses.contains(thirdPart)) {
          print("Answer is " + expense * other * thirdPart)
          sys.exit(0)
        }
      })
    })
  }
}
