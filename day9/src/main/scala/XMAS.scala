object XMAS {
  def main(args: Array[String]) : Unit = {
    val source = scala.io.Source.fromResource("input")
    val exploit = try new Exploit(source.getLines().map(s => s.toLong).toList, 26) finally source.close()
    exploit.exploit()
  }

}
