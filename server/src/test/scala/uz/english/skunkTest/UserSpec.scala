package uz.english.skunkTest

import cats.effect.*
import cats.implicits.{catsSyntaxEq as _, *}
import org.scalacheck.*
import org.scalacheck.Prop.*
import org.scalactic.Prettifier.default
import retry.RetryPolicy
import skunk.Decoder.*
import skunk.data.Completion
import skunk.data.Type.varchar
import skunk.implicits.*
import skunk.{Command, Session}
import uz.english.*
import uz.english.config.DBConfig
import uz.english.db.algebra.UserAlgebra
import uz.english.db.service.UserSql
import uz.english.db.service.UserSql.{insert, selectAll}

import javax.net.ssl.SSLSession

class UserSpec extends StubDatabaseSupport with PureTestSuite with TestEnv {

  test("CREATE user") {
    forAll { (name: String) =>
      IOAssertion {
        for {
          user <- TestDBSession.use(_.prepare(insert).use(_.unique(Username(name))))
        } yield assert(user.name == name)
      }
    }
  }

  test("GET user") {
    IOAssertion {
      for {
        users <- TestDBSession.use(_.execute(selectAll))
      } yield assert(users.nonEmpty)
    }
  }
}


