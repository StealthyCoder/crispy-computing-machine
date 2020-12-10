import scala.util.control.Breaks.{break, breakable}

class PassportValidator(passports: List[String]) {
  private var passport = ""
  private val requirements = Map[String, String => Boolean](
    "byr:" -> validateBYR,
    "iyr:" -> validateIYR,
    "eyr:" -> validateEYR,
    "hgt:" -> validateHGT,
    "hcl:" -> validateHCL,
    "ecl:" -> validateECL,
    "pid:" -> validatePID
  )
  private val optional = List("cid:")
  private var _valid = 0

  def valid: Int = {
    _valid
  }

  def validate(): Unit = {
    passports.foreach(l => {
      if (l.isEmpty) {
        process()
      }
      passport += l.concat(" ")
    })
    process()
  }

  private def process(): Unit = {
    var valid = false
    breakable {
      for ( (fieldName, func) <- requirements) {
        if (fieldExists(passport, fieldName)) {
          valid = func.apply(getFieldString(passport, fieldName))
        } else {
          valid = false
        }

        if (!valid) break
      }
    }
    if (valid) _valid += 1

    reset()
  }

  private def validateBYR(field: String): Boolean = {
    field.toIntOption.exists(i => i >= 1920 && i <= 2002)
  }

  private def validateIYR(field: String): Boolean = {
    field.toIntOption.exists(i => i >= 2010 && i <= 2020)
  }

  private def validateEYR(field: String): Boolean = {
    field.toIntOption.exists(i => i >= 2020 && i <= 2030)
  }

  private def validateHGT(field: String): Boolean = {
    field match {
      case x if x.contains("cm") => x.stripSuffix("cm").toIntOption.exists(i => i >= 150 && i <= 193)
      case x if x.contains("in") => x.stripSuffix("in").toIntOption.exists(i => i >= 59 && i <= 76)
      case _ => false
    }
  }


  private def validateHCL(field: String): Boolean = {
    "^#([0-9]|[a-f]){6}".r matches field
  }

  private def validateECL(field: String): Boolean = {
    List("amb", "blu", "brn", "gry", "grn", "hzl", "oth") contains field
  }

  private def validatePID(field: String): Boolean = {
    field.count(c => c.isDigit) == 9 && field.forall(c => c.isDigit)
  }

  private def getFieldString(passport: String, fieldName: String): String = {
    val start = passport.indexOf(fieldName)
    try {
      passport.slice(start, passport.indexOf(" ", start)).split(":")(1)
    } catch {
      case _: ArrayIndexOutOfBoundsException => passport.substring(start).split(":")(1)
    }
  }

  private def fieldExists(passport: String, fieldName: String): Boolean = {
    passport contains fieldName
  }

  private def reset(): Unit = {
    passport = ""
  }
}