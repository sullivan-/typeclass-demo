trait Comp[A] {
  /** returns less than 0, 0, or greater than zero, depending on whether a1
   * is smaller than a2, equal to a2, or greater than a2 */
  def comp(a1: A, a2: A): Int

  def min: Option[A]
}

object Comp {

  implicit val intComp = new Comp[Int] {
    def comp(a1: Int, a2: Int): Int = a1.compareTo(a2)
    val min = Some(Int.MinValue)
  }

  implicit val stringComp = new Comp[String] {
    def comp(a1: String, a2: String): Int = a1.compareTo(a2)
    val min = Some("")
  }

  def apply[A: Comp]: Comp[A] = implicitly[Comp[A]]

  object ops {
    implicit class CompOps[A: Comp](a: A) {
      def comp(a2: A): Int = Comp[A].comp(a, a2)
    }
  }

  def max[A: Comp](as: Traversable[A]): Option[A] = {
    if (as.isEmpty) {
      None
    } else {
      var max: A = as.head
      as.tail.foreach { a =>
        import ops._
        if (a.comp(max) > 0) max = a
      }
      Some(max)
    }
  }

  implicit def pairComp[A: Comp, B: Comp]: Comp[(A, B)] = new Comp[(A, B)] {
    def comp(a1: (A, B), a2: (A, B)): Int = {
      import ops._
      val comp1 = a1._1.comp(a2._1)
      if (comp1 != 0) {
        comp1
      } else {
        a1._2.comp(a2._2)
      }
    }
    def min = for {
      minA <- implicitly[Comp[A]].min
      minB <- implicitly[Comp[B]].min
    } yield (minA, minB)
  }

}
