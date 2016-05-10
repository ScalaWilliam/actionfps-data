package af

import play.api.libs.functional.Monoid

/**
  * Created by me on 10/05/2016.
  */
package object advanced {
  implicit def mapMonoid[T, V](implicit m: Monoid[V]) = new Monoid[Map[T, V]] {
    override def append(a1: Map[T, V], a2: Map[T, V]): Map[T, V] = {
      (a1.keySet ++ a2.keySet).foldLeft(a1)((map, key) =>
        map.updated(
          key = key,
          value = m.append(a1.getOrElse(key, m.identity), a2.getOrElse(key, m.identity))
        )
      )
    }

    override def identity: Map[T, V] = Map.empty
  }
}
