package uz.english

import io.circe.*
import io.circe.generic.semiauto.*
import uz.english.Username.UsernameType

case class Username (
  name: UsernameType
)

object Username {
  type UsernameType = String

  implicit val usernameDecoder: Decoder[Username] = deriveDecoder[Username]
  implicit val usernameEncoder: Encoder[Username] = deriveEncoder[Username]
}
