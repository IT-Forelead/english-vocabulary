package uz.english.route

import cats.*
import cats.effect.kernel.*
import cats.implicits.*
import io.circe.Encoder
import io.circe.generic.auto.*
import org.http4s.circe.CirceInstances
import org.http4s.dsl.Http4sDsl
import org.http4s.{EntityDecoder, EntityEncoder, HttpRoutes}
import uz.english._
import uz.english.service.WordService

object WordRoutes extends CirceInstances:

  def apply[F[_]: Async](wordService: WordService[F]): HttpRoutes[F] = {
    implicit object dsl extends Http4sDsl[F]; import dsl.*

    HttpRoutes.of[F] {
      case request @ POST -> Root =>
        (for {
          word <- request.as[WordWithoutId]
          _ <- wordService.create(word)
          resp <- Ok(Result("Successfully created!"))
        } yield resp)
          .handleErrorWith { error =>
            BadRequest("Error occurred while create word!")
          }

      case GET -> Root =>
        Ok(wordService.findAll)
    }
  }