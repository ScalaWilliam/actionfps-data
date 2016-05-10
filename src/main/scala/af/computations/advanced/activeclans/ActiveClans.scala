package af.computations.advanced.activeclans

import com.actionfps.gameparser.enrichers.JsonGame
import play.api.libs.functional.{Monoid, Reducer}

/**
  * Created by me on 10/05/2016.
  */
case class ActiveClans(clans: Map[String, Int]) {
  def sortedList: List[(String, Int)] = {
    clans.toList.sortBy { case (_, v) => v }.reverse
  }

  private def includeClan(clan: String, n: Int = 1): ActiveClans = {
    copy(clans = clans.updated(clan, clans.getOrElse(clan, 0) + n))
  }

  def includeGame(jsonGame: JsonGame): ActiveClans = {
    jsonGame
      .teams
      .flatMap(_.players)
      .flatMap(_.clan)
      .foldLeft(this)(_.includeClan(_))
  }

}

object ActiveClans {
  def empty = ActiveClans(clans = Map.empty)

  /** Write these if you'd like to make use of it in a map-reduce framework of some kind */
  implicit val monoid = new Monoid[ActiveClans] {
    override def append(a1: ActiveClans, a2: ActiveClans): ActiveClans =
      a1.clans.foldLeft(a2) { case (curr, (clan, n)) => curr.includeClan(clan, n) }

    override def identity: ActiveClans = ActiveClans.empty
  }

  implicit val reducer: Reducer[JsonGame, ActiveClans] = Reducer(ActiveClans.empty.includeGame)
}
