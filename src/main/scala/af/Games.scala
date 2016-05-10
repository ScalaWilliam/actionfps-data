package af

import java.time.ZonedDateTime

import com.actionfps.gameparser.enrichers.JsonGame

import scala.io.Source

/**
  * Created by me on 10/05/2016.
  */
object Games {
  /** Remember to close it when done with it **/
  def getAll: List[JsonGame] = {
    getIterator match {
      case (src, iter) =>
        try iter.toList
        finally src.close()
    }
  }

  def getIterator: (Source, Iterator[JsonGame]) = {
    val src = scala.io.Source.fromFile("all.txt")
    val iter = src.getLines().map(JsonGame.fromJson)
    (src, iter)
  }

  def iterate[T](f: Iterator[JsonGame] => T): T = {
    getIterator match {
      case (source, iterator) =>
        try f(iterator)
        finally source.close()
    }
  }

  object InPastMonth {
    def iterate[T](f: Iterator[JsonGame] => T): T = {
      val monthAgo = ZonedDateTime.now().minusMonths(1)
      Games.iterate{ games =>
        f {
          games
            .filter(_.endTime.isAfter(monthAgo))
        }
      }
    }
  }

}
