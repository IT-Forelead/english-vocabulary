package uz.english

import cats.effect.kernel.Async
import io.circe.{Decoder, Encoder}
import org.http4s.{EntityDecoder, EntityEncoder}
import uz.english.route.UserRoutes.{jsonEncoderOf, jsonOf}

package object route {
  implicit def deriveEntityEncoder[F[_]: Async, A: Encoder]: EntityEncoder[F, A] = jsonEncoderOf[F, A]

  implicit def deriveEntityDecoder[F[_]: Async, A: Decoder]: EntityDecoder[F, A]  = jsonOf[F, A]
}
