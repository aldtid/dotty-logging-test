enum Common {

  case Cause(value: String)
  case Tag(value: String)

}

object Common {

  given causeLoggable: LoggableAs[Common.Cause, String] {
    def from(cause: Common.Cause): String = s"cause: ${cause.value}"
  }

  given commonLoggable: LoggableAs[Common, String] {
    def from(common: Common): String = common match {
      case cause: Common.Cause => causeLoggable.from(cause)
      case tag: Common.Tag => s"tag: ${tag.value}"
    }
  }

}
