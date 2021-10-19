package uz.english.db.algebra

import cats.effect.kernel.Async
import cats.implicits._
import skunk.Session
import uz.english.{WordWithoutId, Word}

trait WordAlgebra[F[_]]:
  def create(word: WordWithoutId): F[Unit]

  def findAll: F[List[Word]]

object WordAlgebra {

  import uz.english.db.service.WordSql._

  def apply[F[_]: Async](session: Session[F]) =
    new WordAlgebra[F] {
      override def create(word: WordWithoutId): F[Unit] =
        session.prepare(insert).use(_.execute(word)).void

      override def findAll: F[List[Word]] =
        session.execute(selectAll)
    }

}