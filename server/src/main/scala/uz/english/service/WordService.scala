package uz.english.service

import cats.effect.kernel.Async
import cats.effect.std.Console
import uz.english.config.DBConfig
import uz.english.db.module.Database
import uz.english.{Word, WordWithoutId}

trait WordService[F[_]]:
  def create(word: WordWithoutId): F[Unit]
  
  def findAll: F[List[Word]]

object WordService:
  def apply[F[_]: Async: Console](config: DBConfig) =
    new WordService[F]:
      private val database = Database[F](config)

      override def create(word: WordWithoutId): F[Unit] = database.word.use(_.create(word))

      override def findAll: F[List[Word]] = database.word.use(_.findAll)



