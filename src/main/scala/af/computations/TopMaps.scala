package af.computations

import af.Games

/**
  * Created by me on 10/05/2016.
  */
object TopMaps {
  def fully(): Unit = {
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

  def incrementally(): Unit = {
    println("Top maps incrementally:")
    Games.iterate { games =>
      games
        .map(_.map)
        .foldLeft(Map.empty[String, Int])((coll, name) => coll.updated(name, coll.getOrElse(name, 0) + 1))
        .toList
        .sortBy(_._2)
        .takeRight(20)
        .foreach(println)
    }
  }

}
