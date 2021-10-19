package uz.english.db.algebra

import cats.effect.kernel.Async
import cats.implicits
import skunk.Session
import uz.english.{WordWithoutId, Word}

trait WordAlgebra[F[_]]:
  def create(word: WordWithoutId): F[Unit]

  def getWords: F[List[Word]]

object WordAlgebra {

  import uz.english.db.service.WordSql.{insert, getWords}

  def apply[F[_]: Async](session: Session[F]) =
    new WordAlgebra[F] {
      override def create(word: WordWithoutId): F[Unit] =
        session.prepare(insert).use(_.execute(word)).void

      override def getWords: F[List[Word]] =
        session.execute(uz.english.db.service.WordSql.getWords)
    }

}