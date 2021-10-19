package uz.english.db.service

import cats.effect
import cats.effect.kernel.implicits
import cats.effect.{kernel, std}
import cats.implicits._
import cats.syntax.all
import skunk._
import skunk.codec.all._
import skunk.implicits._
import uz.english.{User, WordWithoutId, Word}

object WordSql {

  val codec: Codec[Word] =
    (int4 ~ varchar ~ varchar).imap {
      case (i ~ v ~ d) => Word(i, v, d)
    }(c => c.id ~ c.value ~ c.definition)

  val insert: Command[WordWithoutId] =
    sql"""INSERT INTO "word" VALUES (DEFAULT, $varchar, $varchar)"""
     .command
     .gcontramap[WordWithoutId]

  val selectAll: Query[Void, Word] =
    sql"""SELECT * FROM "word" """.query(codec)
}