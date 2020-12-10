object Passwords {
  def main(args: Array[String]) : Unit = {
    val source = scala.io.Source.fromResource("input")
    val policies = try source.getLines.toList finally source.close()
    val validator = new PasswordPolicyValidator(policies)
    val valid = validator.validate()
    println(valid, policies.size)
  }
}
