package uz.english
import io.circe._
import io.circe.parser._
import io.circe.syntax._

object JsonUtils {
  val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  def toJsonString[T: Encoder](t: T): String = t.asJson.printWith(printer)

  def fromJson[T: Decoder](s: String): T =
    parse(s).fold(throw _, json => json).as[T].fold(throw _, identity)
}
