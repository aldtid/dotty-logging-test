object Main {

  // Union type
  type CommonOrOther = Common | Other

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

  given causeLoggable: LoggableAs[Common.Cause, String] {
    def from(cause: Common.Cause): String = s"cause: ${cause.value}"
  }

  given commonLoggable: LoggableAs[Common, String] {
    def from(common: Common): String = common match {
      case cause: Common.Cause => causeLoggable.from(cause)
      case tag: Common.Tag => s"tag: ${tag.value}"
    }
  }

  given aLoggable: LoggableAs[Other.A, String] {
    def from(a: Other.A): String = s"a: ${a.value}"
  }

  given otherLoggable: LoggableAs[Other, String] {
    def from(other: Other): String = other match {
      case a: Other.A => aLoggable.from(a)
      case b: Other.B => s"b: ${b.value}"
    }
  }

  given commonOrOtherLoggable: LoggableAs[CommonOrOther, String] {
    def from(coo: CommonOrOther): String = coo match {
      case common: Common => commonLoggable.from(common)
      case other: Other => otherLoggable.from(other)
    }
  }

  // Main function
  def main(args: Array[String]): Unit = {

    val logger: LoggerAlgebra = LoggerAlgebra.stdout

    val cause: Common.Cause = new Common.Cause("some cause")
    val tag: Common.Tag = new Common.Tag("hash")

    val a: Other.A = new Other.A("a")
    val b: Other.B = new Other.B(0)

    val initialLog: String = ""

    logCommon(logger, cause, initialLog)
    logOther(logger, a, initialLog)
    logCommonAndOther(logger, tag, b, initialLog)
    logCause(logger, cause, initialLog)
    logA(logger, a, initialLog)
    logCauseAndA(logger, cause, a, initialLog)
    logCommonOrOther(logger, tag, initialLog)
    logCommonOrOther2(logger, tag, b, initialLog)

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

}
