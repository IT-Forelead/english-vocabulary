package uz.english

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import uz.english.WordWithouId.*

case class WordWithouId (
  value: WordType,
  definition: DefinitionType
)

object WordWithouId {
  type WordType = String
  type DefinitionType = String
  
  implicit val usernameDecoder: Decoder[WordWithouId] = deriveDecoder[WordWithouId]
  implicit val usernameEncoder: Encoder[WordWithouId] = deriveEncoder[WordWithouId]
}


