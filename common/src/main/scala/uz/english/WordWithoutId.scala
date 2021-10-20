package uz.english

import io.circe.generic.semiauto._
import io.circe._
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


