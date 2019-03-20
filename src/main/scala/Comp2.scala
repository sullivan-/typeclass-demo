import simulacrum._
import scala.language.implicitConversions

@typeclass trait Comp2[A] {
  /** returns less than 0, 0, or greater than zero, depending on whether a1
   * is smaller than a2, equal to a2, or greater than a2 */
  def comp2(a1: A, a2: A): Int

  def min: Option[A]
}

object Comp2 {

  implicit val intComp2 = new Comp2[Int] {
    def comp2(a1: Int, a2: Int): Int = a1.compareTo(a2)
    val min = Some(Int.MinValue)
  }

  implicit val stringComp2 = new Comp2[String] {
    def comp2(a1: String, a2: String): Int = a1.compareTo(a2)
    val min = Some("")
  }

  def max[A: Comp2](as: Traversable[A]): Option[A] = {
    if (as.isEmpty) {
      None
    } else {
      var max: A = as.head
      as.tail.foreach { a =>
        import ops._
        if (a.comp2(max) > 0) max = a
      }
      Some(max)
    }
  }

  implicit def pairComp[A: Comp2, B: Comp2]: Comp2[(A, B)] = new Comp2[(A, B)] {
    def comp2(a1: (A, B), a2: (A, B)): Int = {
      import ops._
      val comp1 = a1._1.comp2(a2._1)
      if (comp1 != 0) {
        comp1
      } else {
        a1._2.comp2(a2._2)
      }
    }
    def min = for {
      minA <- implicitly[Comp2[A]].min
      minB <- implicitly[Comp2[B]].min
    } yield (minA, minB)
  }

}
