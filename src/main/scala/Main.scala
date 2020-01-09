object Main {

  // Union type
  type CommonOrOther = Common | Other

  // Another union type
  final case class C(value: Boolean)

  type CommonOrOtherOrC = CommonOrOther | C

  // Extension method
  def [A : Semigroup, B](a: A) &(b: B)(given as: LoggableAs[B, A]): A =
    Semigroup[A].combine(a, as.from(b))

  // Given instances
  given stringShow: Show[String] {
    def show(msg: String): String = msg
  }

  given stringSemigroup: Semigroup[String] {
    def combine(s1: String, s2: String): String = s"$s1 | $s2"
  }

  given commonOrOtherLoggable: LoggableAs[CommonOrOther, String] {
    def from(coo: CommonOrOther): String = coo match {
      case common: Common => Common.commonLoggable.from(common)
      case other: Other => Other.otherLoggable.from(other)
    }
  }

  given commonOrOtherOrCLoggable: LoggableAs[CommonOrOtherOrC, String] {
    def from(coooc: CommonOrOtherOrC): String = coooc match {
      //case coo: CommonOrOther => commonOrOtherLoggable.from(coo)
      case common: Common => Common.commonLoggable.from(common)
      case other: Other => Other.otherLoggable.from(other)
      case c: C => s"c: ${c.value}"
    }
  }

  // Main function
  def main(args: Array[String]): Unit = {

    val logger: LoggerAlgebra = LoggerAlgebra.stdout

    val cause: Common.Cause = new Common.Cause("some cause")
    val tag: Common.Tag = new Common.Tag("hash")

    val a: Other.A = new Other.A("a")
    val b: Other.B = new Other.B(0)

    val c: C = C(true)

    val initialLog: String = ""

    logCommon(logger, cause, initialLog)
    logOther(logger, a, initialLog)
    logCommonAndOther(logger, tag, b, initialLog)
    logCause(logger, cause, initialLog)
    logA(logger, a, initialLog)
    logCauseAndA(logger, cause, a, initialLog)
    logCommonOrOther(logger, tag, initialLog)
    logCommonOrOther2(logger, tag, b, initialLog)
    logAll(logger, cause, tag, a, b, initialLog)
    logC(logger, c, initialLog)
    logCommonAndC(logger, cause, c, initialLog)

  }

  // Test functions
  def logCommon[L : Show : Semigroup](logger: LoggerAlgebra, common: Common, log: L)(given LoggableAs[Common, L]): Unit =
    logger.info(log & common)

  def logOther[L : Show : Semigroup](logger: LoggerAlgebra, other: Other, log: L)(given LoggableAs[Other, L]): Unit =
    logger.info(log & other)

  def logCommonAndOther[L : Show : Semigroup](logger: LoggerAlgebra, common: Common, other: Other, log: L)(given LoggableAs[Common, L], LoggableAs[Other, L]): Unit =
    logger.info(log & common & other)

  def logCause[L : Show : Semigroup](logger: LoggerAlgebra, cause: Common.Cause, log: L)(given LoggableAs[Common, L]): Unit =
    logger.info(log & cause)

  def logA[L : Show : Semigroup](logger: LoggerAlgebra, a: Other.A, log: L)(given LoggableAs[Other, L]): Unit =
    logger.info(log & a)

  def logCauseAndA[L : Show : Semigroup](logger: LoggerAlgebra, cause: Common.Cause, a: Other.A, log: L)(given LoggableAs[Common, L], LoggableAs[Other, L]): Unit =
    logger.info(log & cause & a)

  def logCommonOrOther[L : Show : Semigroup](logger: LoggerAlgebra, coo: CommonOrOther, log: L)(given LoggableAs[CommonOrOther, L]): Unit =
    logger.info(log & coo)

  def logCommonOrOther2[L : Show : Semigroup](logger: LoggerAlgebra, coo1: CommonOrOther, coo2: CommonOrOther, log: L)(given LoggableAs[CommonOrOther, L]): Unit =
    logger.info(log & coo1 & coo2)

  def logAll[L : Show : Semigroup](logger: LoggerAlgebra, cause: Common.Cause, tag: Common.Tag, a: Other.A, b: Other.B, log: L)(given LoggableAs[CommonOrOther, L]): Unit =
    logger.info(log & cause & tag & a & b)

  def logC[L : Show : Semigroup](logger: LoggerAlgebra, c: C, log: L)(given LoggableAs[C, L]): Unit =
    logger.info(log & c)

  def logCommonAndC[L : Show : Semigroup](logger: LoggerAlgebra, cause: Common.Cause, c: C, log: L)(given LoggableAs[Common | C , L]): Unit =
    logger.info(log & cause & c)

}
