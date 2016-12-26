import scala.io.Source

/**
  * Created by danielchao on 12/11/15.
  */


case class Command(command: String, pointA: (Int, Int), pointB: (Int, Int))

case class Grid(set: Set[(Int, Int)] = Set.empty) {
  def turnOn(point: (Int, Int)) = Grid(set + point)
  def turnOff(point: (Int, Int)) = Grid(set - point)
  def toggle(point: (Int, Int)) = if (set.contains(point)) turnOff(point) else turnOn(point)
  def numOfLightsOn = set.size
}

object Part1 {

  def execudeCommand(grid: Grid, pointA: (Int, Int), pointB: (Int, Int), command: String) =
    (pointA._1 to pointB._1).foldLeft(grid) { (g, i) =>
      (pointA._2 to pointB._2).foldLeft(g) { (gg, j) =>
        val point = (i, j)
        command match {
          case "turn on" => gg.turnOn(point)
          case "turn off" => gg.turnOff(point)
          case "toggle" => gg.toggle(point)
        }
      }
    }

  def parseLine(line: String) = {
    val regex = """^(turn off|toggle|turn on) (\d{1,3}),(\d{1,3}) through (\d{1,3}),(\d{1,3})$""".r
    line match {
      case regex(command, a1, a2, b1, b2) =>
        Command(command, (a1.toInt, a2.toInt), (b1.toInt, b2.toInt))
      case other => throw new Error("got invalid line")
    }
  }

  def main(args: Seq[String]) = {
    val file = args(0)
    val lines = readFile(file)
    val startingGrid = Grid()
    val endingGrid = lines.foldLeft(startingGrid) { (grid, line) =>
      val cmd = parseLine(line)
      execudeCommand(grid, cmd.pointA, cmd.pointB, cmd.command)
    }
    println(endingGrid.numOfLightsOn)
  }

  def readFile(file: String) = Source.fromFile(file).getLines().toSeq

}

Part1.main(args)