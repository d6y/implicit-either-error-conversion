import cats.syntax.either._

object EitherOps {

  trait ToErr[E1, E2] {
    def apply(e1: E1): E2
  }

  object ToErr {
    def apply[E1, E2](f: E1 => E2) = new ToErr[E1, E2] {
      def apply(e1: E1): E2 = f(e1)
    }
  }

  implicit def autoUp[T, E1, E2](either: Either[E1, T])(implicit convert: ToErr[E1, E2]): Either[E2, T] =
    either.leftMap(err => convert(err))
}

object Example {

  sealed trait AppErr
  case class AppFailed(because: String) extends AppErr

  sealed trait DbErr
  case class DbFailed(code: Int) extends DbErr

  sealed trait ServiceErr
  case class ServiceDown(cause: String) extends ServiceErr
  case class ServiceFailed(cause: String) extends ServiceErr

  import EitherOps._
  implicit val db: ToErr[DbErr, AppErr] = ToErr(_ => AppFailed("datbase is not happy"))
  implicit val s1: ToErr[ServiceErr, AppErr] = ToErr(_ => AppFailed("service is not happy")) 

  def dbCall(x: Int): Either[DbErr, Int] =
    Right(x)

  def serviceCall(x: Int): Either[ServiceErr, Int] =
    Right(x+1)

  def run(): Either[AppErr, Int] = {
    (dbCall(1) : Either[AppErr, Int]).flatMap(serviceCall)
  }
}

object Main {
  def main(args: Array[String]): Unit = 
    println(Example.run())
}
