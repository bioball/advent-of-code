import scala.io.Source
import scala.util.Try

/**
  * Created by danielchao on 12/13/15.
  */

type WireMap = Map[String, Int]

trait BaseCommand {

  val endwire: String

  def withPossibleVal(wire1: String, wire2: String, wires: WireMap)(f: (Int, Int) => Int) = for {
    res1 <- getVal(wire1, wires)
    res2 <- getVal(wire2, wires)
  } yield f(res1, res2)

  def doCmd(w: WireMap) : Option[Int]

  def getVal(wire: String, w: WireMap) = Try(wire.toInt).map(Some(_)).getOrElse(w.get(wire))

}

case class And(wire1: String, wire2: String, endwire: String) extends BaseCommand {
  override def doCmd(w: WireMap) = withPossibleVal(wire1, wire2, w) { (res1, res2) =>
    res1 & res2
  }
}

case class Or(wire1: String, wire2: String, endwire: String) extends BaseCommand {
  override def doCmd(w: WireMap) = withPossibleVal(wire1, wire2, w) { (res1, res2) =>
    res1 | res2
  }
}

case class Lshift(wire1: String, wire2: String, endwire: String) extends BaseCommand {
  override def doCmd (w: WireMap) = withPossibleVal(wire1, wire2, w) { (res1, res2) =>
    res1 << res2
  }
}

case class Rshift(wire1: String, wire2: String, endwire: String) extends BaseCommand {
  override def doCmd (w: WireMap) = withPossibleVal(wire1, wire2, w) { (res1, res2) =>
    res1 >> res2
  }
}

case class Not(wire1: String, endwire: String) extends BaseCommand {
  override def doCmd(w: WireMap) = getVal(wire1, w).map(~_)
}

case class SetCmd(src: String, endwire: String) extends BaseCommand {
  override def doCmd(w: WireMap) = getVal(src, w)
}

object Part1 {

  val regex = """^(\w{1,2})?\s?(OR|AND|NOT|RSHIFT|LSHIFT) (\w{1,2}) -> (\w{1,2})$""".r
  val setCommand = """^(\w{1,10}) -> (\w{1,2})""".r

  val wires: WireMap = Map.empty

  def parseCommand(wire1: String, cmd: String, wire2: String, endwire: String) : BaseCommand = cmd match {
    case "NOT" => Not(wire2, endwire)
    case "OR" => Or(wire1, wire2, endwire)
    case "AND" => And(wire1, wire2, endwire)
    case "LSHIFT" => Lshift(wire1, wire2, endwire)
    case "RSHIFT" => Rshift(wire1, wire2, endwire)
  }

  def parseLine(line: String) = line match {
    case regex(wire1, cmd, wire2, endwire) =>
      parseCommand(wire1, cmd, wire2, endwire)
    case setCommand(value, endwire) => SetCmd(value, endwire)
    case other =>
      throw new Error("Couldn't match, line was " + other)
  }

  def runThroughCmds(tup: (Seq[BaseCommand], WireMap)) : WireMap = {
    val (remainingCmd, wires) = tup
    if (remainingCmd.isEmpty) return wires
    runThroughCmds(remainingCmd.foldLeft((Seq.empty[BaseCommand], wires)) { (tup, cmd) =>
      val (remaining, wires) = tup
      cmd.doCmd(wires).map { result =>
        (remaining, wires ++ Map(cmd.endwire -> result))
      }.getOrElse {
        (remaining ++ Seq(cmd), wires)
      }
    })
  }

  def main(args: Seq[String]) = {
    val lines = readFile(args.head)
    val commands = lines.map(parseLine)
    val wires = Map.empty[String, Int]
    println(runThroughCmds(commands, wires).get("a").get)
  }


  def readFile(file: String) = Source.fromFile(file).getLines().toSeq
}


Part1.main(args)