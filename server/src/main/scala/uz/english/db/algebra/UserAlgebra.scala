package uz.english.db.algebra

import cats.implicits.*
import cats.effect.kernel.Async
import skunk.{Command, Session}
import uz.english.{User, Username}
import uz.english.db.service.UserSql

trait UserAlgebra[F[_]]:
  def create(username: Username): F[Unit]
  
  def findAll: F[List[User]]

object UserAlgebra:
  import UserSql._

  def apply[F[_]: Async](session: Session[F]) =
    new UserAlgebra[F]:
      override def create(username: Username): F[Unit] =
        session.prepare(insert).use(_.execute(username)).void

      override def findAll: F[List[User]] =
        session.execute(selectAll)