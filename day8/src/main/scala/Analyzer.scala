import scala.util.control.Breaks.{break, breakable}

class Analyzer(code: List[String]) {
  private val jmpInstructions: List[Int] = code.indices.filter(i => code(i).startsWith("jmp")).toList
  private val nopInstructions: List[Int] = code.indices.filter(i => code(i).startsWith("nop")).toList

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
