package uz.english

import cats.effect.{IO, Resource}
import cats.implicits.*
import natchez.Trace.Implicits.*
import org.h2.jdbcx.JdbcDataSource
import org.scalactic.source.Position
import org.scalatest.BeforeAndAfterAll
import skunk.Session
import uz.english.StubDatabaseSupport.session

import java.util.UUID
import javax.sql.DataSource
import scala.concurrent.ExecutionContext

trait StubDatabaseSupport:

  val TestDBSession: Resource[IO, Session[IO]] = session

object StubDatabaseSupport:

  private[this] final val HOST = "localhost"
  private[this] final val PORT = 5437
  private[this] final val DATABASE = "english_vocavulary"
  private[this] final val USERNAME = "test"
  private[this] final val PASSWORD = "123"

  val session: Resource[IO, Session[IO]] =
    Session.single(
      host = HOST,
      port = PORT,
      database = DATABASE,
      user = USERNAME,
      password = PASSWORD.some
    )
