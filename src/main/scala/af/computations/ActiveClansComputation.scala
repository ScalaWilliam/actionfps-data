package af.computations

import af.Games
import af.computations.advanced.activeclans.ActiveClans

/**
  * Created by me on 10/05/2016.
  */
object ActiveClansComputation {
  def fully(): Unit = {
    println("Most active clans (players)")
    Games.iterate {
      gms =>
        gms
          .flatMap(_.teams)
          .flatMap(_.players)
          .flatMap(_.clan)
          .toList.groupBy(identity).mapValues(_.length).toList.sortBy(_._2)
          .takeRight(20)
          .foreach(println)
    }
  }

  /**
    * Incremental style to do the same as above.
    * Benefit here is that we don't need to load the data all up front
    * and we can incrementally accept a new game to get a new output
    * whereas in others we'd have to recompute for every new game.
    *
    * This cannot be parallelised. If we wish to parallelise we can use Reducer + Monoids to do that job.
    */
  def incrementally(): Unit = {
    println("Most active clans incrementally")
    Games.iterate(_.foldLeft(ActiveClans.empty)(_.includeGame(_))).sortedList.foreach(println)
  }

  /**
    * But this can be parallelised - so if the above is quite slow, we can rely on
    * Monoid + Reducer type of thing. This is how you should do it if we want to include it in some sort
    * of map-reduce type of framework :-)
    */
  def mapReducely(): Unit = {
    println("Most active clans in map-reduce")
    // parallel
    Games.iterate(_.toList.par.map(ActiveClans.reducer.unit).reduce(ActiveClans.monoid.append)).sortedList.foreach(println)
    // non-parallel
    Games.iterate(_.foldLeft(ActiveClans.monoid.identity)(ActiveClans.reducer.append)).sortedList.foreach(println)
  }

}
