class Interpreter(code: List[String]) {
  private var _accumulator = 0
  private var linesRan: List[(Int, Instruction)] = List.empty
  private var instructionPointer = 0


  private val opCodes: Map[String, Function[Int, Unit]] =
    Map(
      "nop" -> noop,
      "acc" -> accumulate,
      "jmp" -> jump
    )

  def accumulator: Int = _accumulator

  def corrupt: Boolean = instructionPointer != code.size

  def debug(): Unit = {
    for ( line <- linesRan ) {
      println(line._1, line._2)
    }
    println(code.size)
  }

  def interpret(): Unit = {
    do {
      val instruction = parse(code(instructionPointer))
      linesRan :+= Tuple2(instructionPointer, instruction)
      opCodes(instruction.op).apply(instruction.arg)
      if (! instruction.op.equals("jmp")) instructionPointer += 1
    } while ( ! linesRan.exists(i => i._1.equals(instructionPointer)) && instructionPointer < code.size )
  }

  private def parse(s: String): Instruction = {
    val splitted = s.split(" ")
    Instruction(splitted.head, splitted.last.toInt)
  }
  private def noop(i: Int): Unit = {}
  private def accumulate(i: Int): Unit = { _accumulator += i }
  private def jump(i: Int): Unit = { instructionPointer += i }

  private case class Instruction(op: String, arg: Int)
}
