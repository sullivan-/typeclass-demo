# Speaker Notes / Outline

- define trait Comp
- define IntComp and StringComp
- specs for those
- define Comp.ops
- specs for that
- **advantage 1**: we have added functionality to classes that were provided to us.
- define Comps.max
- specs for that
- this demonstrates the main advantage of writing generic code. this is not an advantage over
  subtype polymorphism, which provides an equivalent mechanism. in fact, type classes have a minor
  disadvantage here, as the mechanics of type class implementation bleeds through with the
  `import ops._`.
- define CaseInsensitiveStringComp and specs
- **advantage 2**: Scala type classes allow for multiple implementations. You can't do this with
  sub-typing. It's worth noting that in Haskel, you can't do this. You can only have a single type
  class instance per class. The tradeoff here is that in Scala, you need to deal with all the ugly
  details of a raw implementation with implicits.
- define PairComp and specs
- using shapeless, we could define Comps for any n-tuple, case class, or sealed trait hierarchy,
  assuming that the leaf members of the type all had implicit Comps available.
- **advantage 3**: non-instance methods such as unit/pure
  - see for example cats [Applicative.pure](https://github.com/typelevel/cats/blob/eab04cf135e645930dad549a286edad419880b68/core/src/main/scala/cats/Applicative.scala#L30)
  - define Comp.min and in subclasses
  - define minShouldBeSmaller and test for IntComp.min
- simulacrum:
  - copy Comp to Comp2, CompSpecs to CompSpecs2
  - rm Comp.apply & Comp.ops
  - add @simulacrum.typeclass
  - run tests
- Addenda
  - [Type classes originated circa 1988 in Haskel](https://softwareengineering.stackexchange.com/questions/247023/who-invented-haskells-type-classes)
  - [Type classes may have informed Scala implicits, but it is not clear](https://softwareengineering.stackexchange.com/a/151077)
  - Things look very different in dotty: [Typeclass Derivation](https://dotty.epfl.ch/docs/reference/contextual/derivation.html)
    - Part of the motivation here is to replace an implementation-level language feature (implicits) with something more higher-level and intuitive for programmers.

