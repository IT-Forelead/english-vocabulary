package uz.english.db.algebra

import cats.effect.kernel.Async
import cats.implicits
import skunk.Session
import uz.english.WordWithoutId

trait WordAlgebra[F[_]]:

  def getWords: F[List[WordWithoutId]]

object WordAlgebra:
  def apply[F[_]: Async](session: Session[F]) =
    new WordAlgebra[F]:
      override def getWords: F[List[WordWithoutId]] = ???
      