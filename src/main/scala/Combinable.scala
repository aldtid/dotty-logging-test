/**
 * This abstract class is used to provide extension methods for types to be combined into a final logging structure,
 * which will be shown by a [[Show]] instance.
 *
 * @param a instance
 * @tparam A instance type
 */
abstract class Combinable[A](a: A) {

  /**
   * Combines current [[a]] instance with another instance which can be converted to an [[A]] type.
   *
   * @param b instance
   * @param as conversion instance
   * @tparam B instance type
   * @return the combination between current instance and the converted B instance
   */
  def &[B](b: B)(implicit as: LoggableAs[B, A]): A

}
