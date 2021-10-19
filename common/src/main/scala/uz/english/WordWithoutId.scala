package uz.english

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import uz.english.WordWithoutId.*

case class WordWithoutId (
  value: WordType,
  definition: DefinitionType
)

object WordWithoutId {
  type WordType = String
  type DefinitionType = String
  
  implicit val usernameDecoder: Decoder[WordWithoutId] = deriveDecoder[WordWithoutId]
  implicit val usernameEncoder: Encoder[WordWithoutId] = deriveEncoder[WordWithoutId]
}


