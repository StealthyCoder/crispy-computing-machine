object DeclarationForms {
  def main(args: Array[String]) : Unit = {
    val source = scala.io.Source.fromResource("input")
    val validator = try new DeclarationFormsValidator(source.getLines().toList) finally source.close()
    validator.validate()
    println(validator.affirmatives)
  }
}
