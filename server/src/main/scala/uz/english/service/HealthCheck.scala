
import cats.Parallel
import cats.effect.*
import cats.effect.kernel.*
import cats.effect.kernel.implicits.*
import cats.effect.std.*
import cats.syntax.all.*
import uz.english.domain.AppStatus
import skunk.*
import skunk.codec.all.*
import skunk.implicits.*

import scala.concurrent.duration.*

trait HealthCheck[F[_]]:
  def status: F[AppStatus]

object HealthCheck:
  def apply[F[_]: Sync: Async](
      postgresHealth: Resource[F, PostgresHealth[F]]
  ): F[HealthCheck[F]] =
    Sync[F].delay(
      new HealthCheck[F]:
        override def status: F[AppStatus] =
          postgresHealth.use(_.checkStatus.map(AppStatus.apply))
    )
