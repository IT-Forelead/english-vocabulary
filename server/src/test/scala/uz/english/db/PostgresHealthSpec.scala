//package uz.english.db
//
//import cats.effect.IO
//import org.scalatest.compatible.Assertion
//import org.scalatest.funsuite.AnyFunSuite
//import skunk.*
//import skunk.codec.all.*
//import skunk.implicits.*
//import uz.english.StubDatabaseSupport
//
//class PostgresHealthSpec extends AnyFunSuite with StubDatabaseSupport:
//
//  test("HealthCheck Service") {
//
//    def theTest: IO[Assertion] =
//      for {
//        status <- TestDBSession.map(PostgresHealth[IO](_)).use(_.checkStatus)
//      } yield assert(status == true)
//
//    runAssertions(
//      theTest
//    )
//  }