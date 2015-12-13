import javax.script.ScriptEngineManager

import scala.io.Source

/**
  * Created by danielchao on 12/13/15.
  */


object Part1 {

  val engine = {
    val manager = new ScriptEngineManager()
    manager.getEngineByName("js")
  }

  def evalString(str: String) = engine.eval(str).toString

  def codeLengthCount(lines: Seq[String]) : Int = lines
    .map(evalString)
    .foldLeft(0) { (sum, line) =>
      sum + line.length
    }

  def literalLineCount(lines: Seq[String]) : Int = lines.foldLeft(0) { (sum, line) =>
    sum + line.length
  }

  def main(args: Seq[String]) = {
    val file = args.head
    val lines = readLines(file)
    println(literalLineCount(lines) - codeLengthCount(lines))
  }

  def readLines(file: String) = Source.fromFile(file).getLines().toSeq

}

Part1.main(args)