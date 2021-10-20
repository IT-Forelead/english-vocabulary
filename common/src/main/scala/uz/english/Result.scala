package uz.english

import io.circe.generic.semiauto._
import io.circe._

case class Result(text: String)

object Result {

  implicit val resultDecoder: Decoder[Result] = deriveDecoder[Result]
  implicit val resultEncoder: Encoder[Result] = deriveEncoder[Result]
}
