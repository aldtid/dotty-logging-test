trait Semigroup[A] {

  def combine(a1: A, a2: A): A

}

object Semigroup {

  def apply[A](given semigroup: Semigroup[A]): Semigroup[A] = semigroup

}
