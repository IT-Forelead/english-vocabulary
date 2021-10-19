package uz.english

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import uz.english.Word

case class Word(
  id: Word.WordId,
  value: Word.WordType,
  definition: Word.DefinitionType
)

object Word {
  type WordId = Int
  type WordType = String
  type DefinitionType = String


  implicit val userDecoder: Decoder[Word] = deriveDecoder[Word]
  implicit val userEncoder: Encoder[Word] = deriveEncoder[Word]
}




