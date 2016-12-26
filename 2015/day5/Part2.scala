import scala.io.Source

/**
  * Created by danielchao on 12/10/15.
  */

object Part2 {


  def hasPairOfNonOverlapping(str: String) : Boolean = {
    val pair = str.substring(0,2)
    val rest = str.substring(2)
    if (str.length == 2) return false
    hasPairOfNonOverlapping(str.substring(1)) || rest.zipWithIndex.foldLeft(false) {
      case (hasPair, (char, index)) if index < rest.length - 1 =>
        hasPair || s"$char${rest(index + 1)}" == pair
      case (hasPair, _) =>
        hasPair
    }
  }

  def hasDoubleLetter(str: Seq[Char]) : Boolean = str.zipWithIndex.view.foldLeft(false) {
    case (hasDouble, (char, index)) if index > 0 =>
      hasDouble || char == str(index - 1)
    case other =>
      false
  }

  def hasOneRepeatingLetter(str: String) = {
    val evens = str.zipWithIndex.view.filter { case (str, i) => i % 2 == 0 }.map(_._1).toSeq
    val odds = str.zipWithIndex.view.filter { case (str, i) => i % 2 == 1 }.map(_._1).toSeq
    hasDoubleLetter(evens) || hasDoubleLetter(odds)
  }

  def isNice(str: String) = hasPairOfNonOverlapping(str) && hasOneRepeatingLetter(str)

  def main(args: Array[String]) = {
    val file = args(0)
    val lines = readFile(file)
    val niceLines = lines.count(isNice)
    println(niceLines)
  }

  def readFile(src: String) = Source.fromFile(src).getLines().toSeq
}

Part2.main(args)