import com.actionfps.gameparser.enrichers.JsonGame

/**
  * Created by me on 31/01/2016.
  */
object RunMe extends App {
  def gms = scala.io.Source.fromFile("all.tsv").getLines().map(_.split("\t").toList).collect {
    case id :: json :: _ =>
      val game = JsonGame.fromJson(json)
      game
  }
  def topMaps(): Unit = 
gms.map(_.map).toList.groupBy(identity).mapValues(_.length)
.toList.sortBy(_._2).takeRight(20)
.foreach(println)

topMaps()
//.take(5).foreach(println)
}
