package uz.english.db.service

import cats.effect
import cats.effect.kernel.implicits
import cats.effect.{kernel, std}
import cats.implicits._
import cats.syntax.all
import skunk._
import skunk.codec.all._
import skunk.implicits._
import uz.english.{User, WordWithoutId}

object WordSql:

  val codec: Codec[User] =
    (int4 ~ varchar).imap {
      case (i ~ n) => User(i, n)
    }(c => c.id ~ c.name)

  val insert: Command[WordWithoutId] =
    sql"""INSERT INTO "word" VALUES (DEFAULT, $varchar, $varchar)"""
     .command
     .gcontramap[WordWithoutId]

  val selectAll: Query[Void, User] =
    sql"""SELECT * FROM "word" """.query(codec)
