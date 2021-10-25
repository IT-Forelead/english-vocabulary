package uz.english.skunk

import cats.effect.*
import uz.english.{IOAssertion, PureTestSuite, StubDatabaseSupport, TestEnv, Username}
import uz.english.config.DBConfig
import uz.english.service.UserService
import cats.implicits.{catsSyntaxEq as _, *}
import org.scalacheck.Arbitrary
import org.scalacheck.Prop.forAllNoShrink
import retry.RetryPolicy
import skunk.data.Type.varchar
import skunk.Decoder.*
import skunk.data.Completion
import skunk.implicits.*
import skunk.{Command, Session}
import uz.english.db.algebra.UserAlgebra
import uz.english.db.service.UserSql
import uz.english.db.service.UserSql.{insert, selectAll}

import javax.net.ssl.SSLSession
import org.scalacheck._
import Prop._

class UserSpec extends StubDatabaseSupport with PureTestSuite with TestEnv {

//  test("create user") {
//    forAll { (_: String) =>
//      val username = Username(name = "Daniel")
//      IOAssertion {
//        for {
//          _ <- TestDBSession.use(_.prepare(insert).use(_.execute(username)))
//        } yield username
//      }
//    }
//  }

  test("GET user") {
    IOAssertion {
      for {
        users <- TestDBSession.use(_.execute(selectAll))
      } yield assert(users.length == 3)
    }
  }
}


