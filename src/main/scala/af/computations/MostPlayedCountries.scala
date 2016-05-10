package af.computations

import af.Games

/**
  * Created by me on 10/05/2016.
  */
object MostPlayedCountries {
  def fully(): Unit = {
    println("Most played countries")
    Games.iterate { gms =>
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

  def incrementally(): Unit = {
    Games.iterate { gms =>
      gms
        .flatMap(_.teams)
        .flatMap(_.players)
        .flatMap(_.countryName)
        .foldLeft(Map.empty[String, Int])((map, country) => map.updated(country, map.getOrElse(country, 0) + 1))
        .toList
        .sortBy(_._2)
        .takeRight(20)
        .foreach(println)
    }
  }
}
