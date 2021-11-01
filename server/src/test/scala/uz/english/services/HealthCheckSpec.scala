//package uz.english.services
//
//import cats.effect.*
//import cats.effect.unsafe.implicits.global
//import cats.implicits.*
//import uz.english.db.PostgresHealth
//import uz.english.domain.AppStatus
//import org.scalatest.Assertion
//import uz.english.TestEnv
//
//class HealthCheckSpec extends TestEnv:
//
//  class FakePostgresHealth(shouldFail: Boolean) extends PostgresHealth[IO]:
//    override def checkStatus =
//      if (shouldFail)
//        IO.raiseError(new Exception("Error"))
//      else
//        IO.pure(true)
//
//  test("HealthCheck Service") {
//
//    def postgresHealth(shouldFail: Boolean) =
//      Resource.eval(IO.pure(new FakePostgresHealth(shouldFail)))
//
//    def theTest(shouldFail: Boolean): IO[Assertion] =
//      (for {
//        service <- HealthCheck[IO](postgresHealth(shouldFail))
//        status  <- service.status
//      } yield assert(status == AppStatus(true)))
//        .handleError { error =>
//          assert(shouldFail && error.getMessage.contains("Error"))
//        }
//
//    runAssertions(
//      theTest(false),
//      theTest(true)
//    )
//  }
//
//
//
