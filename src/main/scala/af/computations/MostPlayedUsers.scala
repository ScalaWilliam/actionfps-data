package af.computations

import af.Games
import af.computations.advanced.activeclans.ActiveClans

/**
  * Created by me on 10/05/2016.
  */
object MostPlayedUsers {
  def fully(): Unit = {
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

  def incrementally(): Unit = {
    println("Most played users incrementally:")
    Games.iterate { gms =>
      gms
        .flatMap(_.teams)
        .flatMap(_.players)
        .flatMap(_.user)
        .foldLeft(Map.empty[String, Int])((map, user) => map.updated(user, map.getOrElse(user, 0) + 1))
        .toList
        .sortBy(_._2)
        .takeRight(20)
        .foreach(println)
    }
  }
}




