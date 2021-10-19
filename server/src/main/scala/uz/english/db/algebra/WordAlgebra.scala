package uz.english.db.algebra

import cats.effect.kernel.Async
import cats.implicits
import skunk.Session
import uz.english.WordWithoutId
import uz.english.db.service.WordSql

trait WordAlgebra[F[_]]:
  def create(word: WordWithoutId): F[Unit]

  def getWords: F[List[WordWithoutId]]

object WordAlgebra:
  import WordSql._

  def apply[F[_]: Async](session: Session[F]) =
    new WordAlgebra[F]:
      override def create(word: WordWithoutId): F[Unit] =
        session.prepare(insert).use(_.execute(word)).void

      override def getWords: F[List[WordWithoutId]] = ???
