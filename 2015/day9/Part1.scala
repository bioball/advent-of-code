import scala.io.Source
import scala.collection.mutable

/**
  * Created by danielchao on 12/13/15.
  */

case class City(name: String, routes: Map[String, Int]) {
  def addRoute(c: City, distance: Int) = routes ++ Map(c.name, distance)
}
case class Route(city1: String, city2: String, distance: Int)

object Part1 {

  var cities : Set[City] = Set.empty

  def parseLine(line: String) = line.split(" ").toSeq match {
    case Seq(cityName1, "to", cityName2, "=", distance) =>
      Route(cityName1, cityName2, distance.toInt)
    case other => throw new Error("Got unmatched line " + other)
  }

  def main(args: Seq[String]) = {
    val file = args.head
    val lines = readFile(file)
    lines.map(parseLine).foldLeft(Set.empty[City]) { (set, route) =>
      val city1 = getCity(route.city1, set)
      val city2 = getCity(route.city2, set)
      set ++ (
        city1
      )
    }
  }

  def getCity(name: String, set: Set[City]) = {
    set.find(_.name == name).getOrElse(City(name, Map.empty))
  }

  def readFile(src: String) = {
    Source.fromFile(src).getLines.toSeq
  }
}