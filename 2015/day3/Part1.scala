import scala.io.Source

/**
  * Created by danielchao on 12/10/15.
  */

case class Santa(
 matrix: Map[(Int, Int), Int],
 current: (Int, Int)
) {

  val (x, y) = current

  def moveUp : Santa = move (x, y - 1)
  def moveDown : Santa = move (x, y + 1)
  def moveLeft : Santa = move (x - 1, y)
  def moveRight : Santa = move (x + 1, y)

  def foo(pos: Option[Int]) = "foo"

  def move(pos: (Int, Int)) = {
    val newMatrix = matrix ++ Map(pos -> matrix.get(pos).map(_ + 1).getOrElse(1))
    Santa(newMatrix, pos)
  }

}

object Counter {

  def main(args: Array[String]) = {
    val file = args(0)
    val pos = (0,0)
    val santa = Santa(matrix = Map(pos -> 1), current = pos)
    val grid = placePresents(readFile(file), santa).matrix
    println(grid.keys.toSeq.length)
  }

  def placePresents(directions: Seq[Char], s: Santa) : Santa = directions.foldLeft (s) {
    case (santa, '^') =>
      santa.moveUp
    case (santa, '>') =>
      santa.moveRight
    case (santa, 'v') =>
      santa.moveDown
    case (santa, '<') =>
      santa.moveLeft
    case other =>
      throw new Error("somehow got a non-move command")
  }

  def readFile(src: String) = Source.fromFile(src).toSeq
}

Counter.main(args)