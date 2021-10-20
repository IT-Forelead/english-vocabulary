package uz.english

import io.circe._
import io.circe.generic.semiauto._
import uz.english.Username.UsernameType

case class Username (
  name: UsernameType
)

object Username {
  type UsernameType = String

  implicit val usernameDecoder: Decoder[Username] = deriveDecoder[Username]
  implicit val usernameEncoder: Encoder[Username] = deriveEncoder[Username]
}
