import com.actionfps.gameparser.enrichers.JsonGame

/**
  * Created by me on 31/01/2016.
  */
object RunMe extends App {
  scala.io.Source.fromFile("all.tsv").getLines().map(_.split("\t").toList).collect {
    case id :: json :: _ =>
      val game = JsonGame.fromJson(json)
      game
  }.take(5).foreach(println)
}
