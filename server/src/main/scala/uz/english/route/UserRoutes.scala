package uz.english.route

import cats.*
import cats.effect.kernel.*
import org.http4s.{EntityDecoder, EntityEncoder, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import cats.implicits.*
import io.circe.Encoder
import io.circe.generic.auto.*
import org.http4s.circe.CirceInstances
import uz.english.JsonUtils.fromJson
import uz.english._
import uz.english.service.UserService

object UserRoutes extends CirceInstances:

  def apply[F[_]: Async](userService: UserService[F]): HttpRoutes[F] = {
    implicit object dsl extends Http4sDsl[F]; import dsl._

    HttpRoutes.of[F] {
      case request @ POST -> Root =>
        (for {
          user <- request.as[Username]
          _ <- userService.create(user)
          resp <- Ok(Result("Successfully created!"))
        } yield resp)
          .handleErrorWith { error =>
            BadRequest("Error occurred while create user!")
          }

      case GET -> Root =>
        Ok(userService.findAll)
    }
  }