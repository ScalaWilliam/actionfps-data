package af.computations

import af.Games

/**
  * Created by me on 10/05/2016.
  */
object MostPlayedUnregisteredPlayers {
  def fully(): Unit = {
    println("Most played unregistered players")
    Games.iterate { gms =>
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

  def incrementally(): Unit = {
    println("Most played unregistered players")
    Games.iterate { gms =>
      gms
        .flatMap(_.teams)
        .flatMap(_.players)
        .filter(_.user.isEmpty)
        .map(_.name)
        .foldLeft(Map.empty[String, Int])((map, name) => map.updated(name, map.getOrElse(name, 0) + 1))
        .toList
        .sortBy(_._2)
        .takeRight(20)
        .foreach(println)
    }
  }

}
