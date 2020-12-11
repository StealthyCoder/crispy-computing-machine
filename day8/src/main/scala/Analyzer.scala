import scala.util.control.Breaks.{break, breakable}

class Analyzer(code: List[String]) {
  private var jmpInstructions: List[Int] = List.empty
  private var nopInstructions: List[Int] = List.empty

  for (i <- code.indices) {
    if (code(i).startsWith("jmp")) jmpInstructions :+= i
    else if (code(i).startsWith("nop")) nopInstructions :+= i
  }

  def analyze(): Unit = {
    simulate(nopInstructions, "nop", "jmp")
    simulate(jmpInstructions, "jmp", "nop")
  }

  private def simulate(indices: List[Int], command: String, replacement: String): Unit = {
    breakable {
      for ( index <- indices ) {
        val interpreter = new Interpreter(
          code.updated(
            index,
            code(index).replace(command, replacement)
          )
        )
        interpreter.interpret()
        if (! interpreter.corrupt ) {
          println("Accumulator is " + interpreter.accumulator )
          break
        }
      }
    }
  }
}
