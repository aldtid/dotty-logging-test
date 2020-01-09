enum Other {

  case A(value: String)
  case B(value: Int)

}

object Other {

  given aLoggable: LoggableAs[Other.A, String] {
    def from(a: Other.A): String = s"a: ${a.value}"
  }

  given otherLoggable: LoggableAs[Other, String] {
    def from(other: Other): String = other match {
      case a: Other.A => aLoggable.from(a)
      case b: Other.B => s"b: ${b.value}"
    }
  }

}
