import scala.io.Source

scala.util.matching.Regex

/**
  * Created by danielchao on 12/10/15.
  */

object Part1 {

  def containsThreeVowels(s: String) = s.filter(isVowel).length >= 3

  def isVowel(c: Char) = Seq('a', 'e', 'i', 'o', 'u').contains(c)

  def hasDoubleLetter(str: String) = str.zipWithIndex.view.foldLeft(false) {
    case (hasDouble, (char, index)) if index > 0 =>
      hasDouble || char == str(index - 1)
    case other =>
      false
  }

  def doesNotHaveForbidden(str: String) = "(ab|cd|pq|xy)".r.findFirstIn(str).toSeq.isEmpty

  def isNice(str: String) = containsThreeVowels(str) && hasDoubleLetter(str) && doesNotHaveForbidden(str)

  def main(args: Array[String]) = {
    val file = args(0)
    val lines = readFile(file)
    val numberOfNiceLines = lines.count(isNice)
    println(numberOfNiceLines)
  }

  def readFile(src: String) = Source.fromFile(src).getLines().toSeq
}

Part1.main(args)