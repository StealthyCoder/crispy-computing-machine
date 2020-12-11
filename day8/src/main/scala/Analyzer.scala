import scala.util.control.Breaks.{break, breakable}

class Analyzer(code: List[String]) {
  private var jmpInstructions: List[Int] = List.empty
  private var nopInstructions: List[Int] = List.empty

  for (i <- code.indices) {
    if (code(i).startsWith("jmp")) jmpInstructions :+= i
    else if (code(i).startsWith("nop")) nopInstructions :+= i
  }

  def analyze(): Unit = {
    breakable {
      for (instruction <- nopInstructions) {
        val interpreter = new Interpreter(
          code.updated(
            instruction,
            code(instruction).replace("nop", "jmp")
          )
        )
        interpreter.interpret()
        if (! interpreter.corrupt ) {
          println("Accumulator is " + interpreter.accumulator )
          break
        }
      }

      for (instruction <- jmpInstructions) {
        val interpreter = new Interpreter(
          code.updated(
            instruction,
            code(instruction).replace("jmp", "nop")
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
