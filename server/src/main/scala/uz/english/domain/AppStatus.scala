package uz.english.domain

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import uz.english.domain.AppStatus.*

case class AppStatus(
  postgres: PostgresStatus
)

object AppStatus:
  type PostgresStatus = Boolean
  implicit val userDecoder: Decoder[AppStatus] = deriveDecoder[AppStatus]
  implicit val userEncoder: Encoder[AppStatus] = deriveEncoder[AppStatus]
