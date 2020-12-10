import java.util.concurrent.atomic.AtomicInteger

class PasswordPolicyValidator(passwords: List[String]) {
  private val _valid: AtomicInteger = new AtomicInteger(0)

  def validate(): Int = {
    passwords.foreach(s => {
      val passwordPolicy = segment(s)
      if (_validate(passwordPolicy)) {
        _valid.getAndIncrement()
      }
    })
    _valid.get()
  }

  private def segment(s: String): PasswordPolicy = {
    val split = s.split(" ")
    PasswordPolicy(split.head, split(1), split(2))
  }

  private def positions(s: String): Positions = {
    val min_max = s.split("-")
    Positions(min_max.head.toInt - 1, min_max(1).toInt - 1)
  }

  private def letter(s: String): Char = {
    s.head
  }

  private def _validate(policy: PasswordPolicy): Boolean = {
    val pos = positions(policy.min_max)
    val char = letter(policy.letter)
    if (policy.password.charAt(pos.min) == char || policy.password.charAt(pos.max) == char) {
      return policy.password.charAt(pos.min) != policy.password.charAt(pos.max)
    }
    false
  }

  private case class PasswordPolicy(min_max: String, letter: String, password: String)
  private case class Positions(min: Int, max: Int)

}

