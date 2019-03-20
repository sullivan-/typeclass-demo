import org.scalatest._
import Comp.ops._

class CompSpec extends FlatSpec with Matchers {

  "Comp.intComp" should "compare ints properly" in {
    Comp.intComp.comp(0, -7) should be > 0
    Comp.intComp.comp(0, 0) should be (0)
    Comp.intComp.comp(0, 7) should be < 0
  }

  "Comp.stringComp" should "compare strings properly" in {
    Comp.stringComp.comp("abc", "abb") should be > 0
    Comp.stringComp.comp("abc", "abc") should be (0)
    Comp.stringComp.comp("abc", "abd") should be < 0
  }

  "Comp.ops" should "provide a more convenient syntax" in {
    0.comp(-7) should be > 0
    0.comp(0) should be (0)
    0.comp(7) should be < 0
    "abc".comp("abb") should be > 0
    "abc".comp("abc") should be (0)
    "abc".comp("abd") should be < 0
  }

  "Comp.max" should "take the max element from a sequence" in {
    Comp.max(Seq[Int]()) should be (None)
    Comp.max(Seq(5, 4, 3, 7, 6)) should be (Some(7))
    Comp.max(Seq("", "abc", "abd", "abb")) should be (Some("abd"))
  }

  "Comp" should "allow for user defined alternative implementations" in {
    implicit val caseInsensitiveStringComp = new Comp[String] {
      def comp(a1: String, a2: String): Int = 
        Comp.stringComp.comp(a1.toLowerCase, a2.toLowerCase)
      def min = Some("")
    }
    "abc".comp("abb") should be > 0
    "abc".comp("ABB") should be > 0
    "abc".comp("abc") should be (0)
    "abc".comp("ABC") should be (0)
    "abc".comp("abd") should be < 0
    "abc".comp("ABD") should be < 0
  }

  "Comp.PairComp" should "compare pairs properly" in {
    (0, "abc").comp((-7, "abc")) should be > 0
    (0, "abc").comp((0, "abb")) should be > 0
    (0, "abc").comp((0, "abc")) should be (0)
    (0, "abc").comp((0, "abd")) should be < 0
    (0, "abc").comp((7, "abc")) should be < 0
  }

  private def minShouldBeSmaller[A: Comp](a: A): Unit = Comp[A].min.foreach { m =>
    m.comp(a) should be <= 0
    a.comp(m) should be >= 0
  }

  "intComp.min" should "be smaller than any other int value" in {
    minShouldBeSmaller(Int.MinValue)
    minShouldBeSmaller(-7)
    minShouldBeSmaller(0)
    minShouldBeSmaller(7)
  }

}
