trait Show[A] {

  def show(a: A): String

}

object Show {

  def apply[A](given show: Show[A]): Show[A] = show

}
