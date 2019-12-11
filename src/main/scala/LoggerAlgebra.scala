/**
 * Logging trait that exposes the different logging levels.
 */
trait LoggerAlgebra {

  /**
   * Informative logging level.
   *
   * @param message message
   * @tparam A message type
   * @return an effect of unit
   */
  def info[A : Show](message: A): Unit

  /**
   * Warning logging level.
   *
   * @param message message
   * @tparam A message type
   * @return an effect of unit
   */
  def warn[A : Show](message: A): Unit

  /**
   * Debugging logging level.
   *
   * @param message message
   * @tparam A message type
   * @return an effect of unit
   */
  def debug[A : Show](message: A): Unit

  /**
   * Error logging level.
   *
   * @param message message
   * @tparam A message type
   * @return an effect of unit
   */
  def error[A : Show](message: A): Unit

  /**
   * Fatal logging level.
   *
   * @param message message
   * @tparam A message type
   * @return an effect of unit
   */
  def fatal[A : Show](message: A): Unit

}

object LoggerAlgebra {

  /**
   * Creates a new logger algebra with passed functions as its logging level functions.
   *
   * @param infoF info level function
   * @param warnF warn level function
   * @param debugF debug level function
   * @param errorF error level function
   * @param fatalF fatal level function
   * @return a logger algebra
   */
  def apply(infoF: String => Unit,
            warnF: String => Unit,
            debugF: String => Unit,
            errorF: String => Unit,
            fatalF: String => Unit): LoggerAlgebra =

    new LoggerAlgebra {

      def info[A: Show](m: A): Unit = infoF(Show[A].show(m))
      def warn[A: Show](m: A): Unit = warnF(Show[A].show(m))
      def debug[A: Show](m: A): Unit = debugF(Show[A].show(m))
      def error[A: Show](m: A): Unit = errorF(Show[A].show(m))
      def fatal[A: Show](m: A): Unit = fatalF(Show[A].show(m))

    }

  /**
   * Performs the log of an string via stdout wrapping the impure logging function with a delayed effect context.
   *
   * @param level logging level
   * @return an effect of unit
   */
  def stdoutLog(level: String): String => Unit =
    message => println(f"[$level%5s] ${System.currentTimeMillis()} : $message")

  /**
   * Creates a logger algebra that logs via stdout.
   *
   * @return a logger algebra that uses stdout to log
   */
  def stdout: LoggerAlgebra =
    apply(stdoutLog("INFO"), stdoutLog("WARN"), stdoutLog("DEBUG"), stdoutLog("ERROR"), stdoutLog("FATAL"))

}
