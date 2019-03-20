import org.scalatest._
import Comp2.ops._

class Comp2Spec extends FlatSpec with Matchers {

  "Comp2.IntComp2" should "compare ints properly" in {
    Comp2.IntComp2.comp2(0, -7) should be > 0
    Comp2.IntComp2.comp2(0, 0) should be (0)
    Comp2.IntComp2.comp2(0, 7) should be < 0
  }

  "Comp2.StringComp2" should "compare strings properly" in {
    Comp2.StringComp2.comp2("abc", "abb") should be > 0
    Comp2.StringComp2.comp2("abc", "abc") should be (0)
    Comp2.StringComp2.comp2("abc", "abd") should be < 0
  }

  "Comp2.ops" should "provide a more convenient syntax" in {
    0.comp2(-7) should be > 0
    0.comp2(0) should be (0)
    0.comp2(7) should be < 0
    "abc".comp2("abb") should be > 0
    "abc".comp2("abc") should be (0)
    "abc".comp2("abd") should be < 0
  }

  "Comp2.max" should "take the max element from a sequence" in {
    Comp2.max(Seq[Int]()) should be (None)
    Comp2.max(Seq(5, 4, 3, 7, 6)) should be (Some(7))
    Comp2.max(Seq("", "abc", "abd", "abb")) should be (Some("abd"))
  }

  "Comp2" should "allow for user defined alternative implementations" in {
    implicit object CaseInsensitiveStringComp2 extends Comp2[String] {
      def comp2(a1: String, a2: String): Int = 
        Comp2.StringComp2.comp2(a1.toLowerCase, a2.toLowerCase)
      def min = Some("")
    }
    "abc".comp2("abb") should be > 0
    "abc".comp2("ABB") should be > 0
    "abc".comp2("abc") should be (0)
    "abc".comp2("ABC") should be (0)
    "abc".comp2("abd") should be < 0
    "abc".comp2("ABD") should be < 0
  }

  "Comp2.PairComp2" should "compare pairs properly" in {
    (0, "abc").comp2((-7, "abc")) should be > 0
    (0, "abc").comp2((0, "abb")) should be > 0
    (0, "abc").comp2((0, "abc")) should be (0)
    (0, "abc").comp2((0, "abd")) should be < 0
    (0, "abc").comp2((7, "abc")) should be < 0
  }

  private def minShouldBeSmaller[A: Comp2](a: A): Unit = Comp2[A].min.foreach { m =>
    m.comp2(a) should be <= 0
    a.comp2(m) should be >= 0
  }

  "IntComp2.min" should "be smaller than any other int value" in {
    minShouldBeSmaller(Int.MinValue)
    minShouldBeSmaller(-7)
    minShouldBeSmaller(0)
    minShouldBeSmaller(7)
  }

}
