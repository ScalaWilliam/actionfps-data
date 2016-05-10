package af

import af.computations._

/**
  * Created by me on 31/01/2016.
  *
  * There are a bunch of different ways to perform these computations:
  *
  * 1. Fully - here we load in all the data at once and process that.
  * Advantages:
  * * Simple to design and reason about
  * Disadvantages:
  * * May be slow to recompute for every new game coming in
  *
  * 2. Incrementally - here we start from an initial state and introduce a new item to it
  * Advantages:
  * * Can be used in streaming and don't have to recompute from scratch
  * Disadvantages:
  * * Cannot be parallelised
  * * More difficult to design
  *
  * 3. Monoidically with a reducer:
  * Advantages:
  * * Can be parallelised and streamed
  * Disadvantages:
  * * More difficult to design and more code to write
  *
  */
object RunMe extends App {

  TopMaps.fully()
  TopMaps.incrementally()

  MostPlayedUsers.fully()
  MostPlayedUsers.incrementally()

  MostPlayedUnregisteredPlayers.fully()
  MostPlayedUnregisteredPlayers.incrementally()

  ActiveClansComputation.fully()
  ActiveClansComputation.incrementally()
  ActiveClansComputation.mapReducely()

  MostPlayedCountries.fully()
  MostPlayedCountries.incrementally()

}

