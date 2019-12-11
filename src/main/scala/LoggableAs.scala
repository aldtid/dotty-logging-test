/**
 * Defines a type that can be converted into a logging type. Basically, instances of this type define the serialization
 * for those types into an expected logging type (for which there should be a [[Show]] instance to be shown in a
 * [[LoggerAlgebra]] logging function).
 *
 * [[A]] type is contravariant to allow inference for ADTs subtypes (for which an ADT instance must be defined).
 *
 * @tparam A convertible type
 * @tparam B logging type
 */
trait LoggableAs[-A, B] {

  /**
   * Converts an instance into its serialized logging representation.
   *
   * @param a instance
   * @return the logging representation for a
   */
  def from(a: A): B

}

object LoggableAs {

  // Basic instance to allow the combination of instances of same type.
  //implicit def self[A]: LoggableAs[A, A] = identity

}
