
import cats._
import cats.implicits.*
import cats.effect.kernel.*
import cats.effect.kernel.implicits.*
import cats.effect.std.*
import uz.english.domain.AppStatus.PostgresStatus
import skunk.codec.all.*
import skunk.implicits.*
import skunk.*

trait PostgresHealth[F[_]]:
  def checkStatus: F[PostgresStatus]

object PostgresHealth:
  def apply[F[_]: Async](
      sessionPool: Session[F]
  ): PostgresHealth[F] =
    new PostgresHealth[F]:

      private[this] val check: Query[Void, Int] =
        sql"SELECT pid FROM pg_stat_activity".query(int4)

      override def checkStatus: F[PostgresStatus] =
        sessionPool
            .execute(check)
            .map(_.nonEmpty)
            .orElse(false.pure[F])
