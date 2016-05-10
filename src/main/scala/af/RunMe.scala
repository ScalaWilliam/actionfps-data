package af

import java.time.ZonedDateTime

import com.actionfps.gameparser.enrichers.JsonGame

/**
  * Created by me on 31/01/2016.
  */
object RunMe extends App {

  def topMaps(): Unit = {
    println("Top maps:")
    Games.iterate { games =>
      games
        .map(_.map)
        .toList
        .groupBy(identity)
        .mapValues(_.length)
        .toList
        .sortBy(_._2)
        .takeRight(20)
        .foreach(println)
    }
  }

  def mostPlayedUsers(): Unit = {
    println("Most played users:")
    Games.iterate { gms =>
      gms
        .flatMap(_.teams)
        .flatMap(_.players)
        .flatMap(_.user)
        .toList
        .groupBy(identity)
        .mapValues(_.length)
        .toList
        .sortBy(_._2)
        .takeRight(20)
        .foreach(println)
    }
  }

  def mostPlayedUnregisteredPlayersInPastMonth(): Unit = {
    println("Most played recent unregistered players")
    Games.InPastMonth.iterate { gms =>
      gms
        .flatMap(_.teams)
        .flatMap(_.players)
        .filter(_.user.isEmpty)
        .map(_.name)
        .toList
        .groupBy(identity)
        .mapValues(_.length)
        .toList
        .sortBy(_._2)
        .takeRight(20)
        .foreach(println)
    }
  }

  def mostPlayedCountriesRecently(): Unit = {
    println("Most played countries recently")
    Games.InPastMonth.iterate { gms =>
      gms
        .flatMap(_.teams)
        .flatMap(_.players)
        .flatMap(_.countryName)
        .toList
        .groupBy(identity)
        .mapValues(_.length)
        .toList
        .sortBy(_._2)
        .takeRight(20)
        .foreach(println)
    }
  }

  topMaps()
  println("")
  mostPlayedUsers()
  println("")
  mostPlayedUnregisteredPlayersInPastMonth()
  println("")
  mostPlayedCountriesRecently()

}
