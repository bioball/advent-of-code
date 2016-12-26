import scala.io.Source

/**
  * Created by danielchao on 12/10/15.
  *
  * --- Part Two ---
  *
  * The next year, to speed up the process, Santa creates a robot version of himself, Robo-Santa, to deliver presents with him.
  *
  * Santa and Robo-Santa start at the same location (delivering two presents to the same starting house), then take turns moving based on instructions from the elf, who is eggnoggedly reading from the same script as the previous year.
  *
  * This year, how many houses receive at least one present?
  *
  * For example:
  *
  * ^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
  * ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
  * ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
  */

case class Santa(
  matrix: Map[(Int, Int), Int],
  current: (Int, Int)
) {

  val (x, y) = current

  def moveFromInstruction(c: Char) = c match {
    case '^' => moveUp
    case '>' => moveRight
    case 'v' => moveDown
    case '<' => moveLeft
  }

  def moveUp : Santa = move (x, y - 1)
  def moveDown : Santa = move (x, y + 1)
  def moveLeft : Santa = move (x - 1, y)
  def moveRight : Santa = move (x + 1, y)

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
    val robot = Santa(matrix = Map(pos -> 1), current = pos)
    val (grid1, grid2) = placePresents(readFile(file), santa, robot)
    println((grid1 ++ grid2).keys.toSeq.length)
  }

  def placePresents(directions: Seq[Char], s: Santa, r: Santa) = {
    val (postSanta, postRobot) = directions.view.zipWithIndex.foldLeft ((s, r)) {
      case ((santa, robot), (char, index)) if santaShouldMove(index) =>
        (santa.moveFromInstruction(char), robot)
      case ((santa, robot), (char, index)) if !santaShouldMove(index) =>
        (santa, robot.moveFromInstruction(char))
    }
    (postSanta.matrix, postRobot.matrix)
  }

  def santaShouldMove(index: Int) = index % 2 == 0

  def readFile(src: String) = Source.fromFile(src).toSeq
}

Counter.main(args)