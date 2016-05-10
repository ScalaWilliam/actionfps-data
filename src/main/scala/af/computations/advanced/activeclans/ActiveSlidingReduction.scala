package af.computations.advanced.activeclans

import java.time.{Period, ZonedDateTime}

import com.actionfps.gameparser.enrichers.JsonGame

import scala.collection.immutable.ListMap

/**
  * Created by me on 10/05/2016.
  * Sliding window method of calculating, say, a 1 month sliding window for each of the games.
  *
  * This is just experimentation.
  */
case class ActiveSlidingReduction(period: Period) {

  case class Slider(clans: Map[String, Int], builtFrom: ListMap[ZonedDateTime, List[String]]) {
    private def minusClan(clan: String): Slider = {
      clans.get(clan) match {
        case Some(v) if v == 1 => copy(clans = clans - clan)
        case Some(v) => copy(clans = clans.updated(clan, v - 1))
        case None => this
      }
    }

    def cutOff(before: ZonedDateTime): Slider = {
      builtFrom.headOption match {
        case Some((zdt, clns)) if zdt.isBefore(before) =>
          clns
            .foldLeft(this)(_.minusClan(_))
            .copy(builtFrom = builtFrom.tail)
            .cutOff(before)
        case _ => this
      }
    }

    def include(jsonGame: JsonGame): Slider = {
      val gameClans = jsonGame.teams.flatMap(_.players).flatMap(_.clan)
      val newClans = gameClans.foldLeft(clans)((clansL, clan) =>
        clansL.updated(clan, clansL.getOrElse(clan, 0) + 1)
      )
      copy(
        clans = newClans,
        builtFrom = builtFrom + (jsonGame.endTime -> gameClans)
      ).cutOff(jsonGame.endTime.minus(period))
    }
  }

}
