import java.security.MessageDigest

/**
  * Created by danielchao on 12/10/15.
  */


object Program {
  val input = "bgvyzdsv"

  def md5(s: String) = MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8")).map("%02x".format(_)).mkString

  def beginsWithFiveZeroes(s: String) = s.startsWith("00000")

  def main() = {
    for (i <- 1 to 1000000) {
      val hashed = md5(input + i.toString)
      if (beginsWithFiveZeroes(hashed)) {
        println(i)
        System.exit(0)
      }
    }
    println("couldnt find answer.")
    System.exit(1)
  }
}

Program.main()