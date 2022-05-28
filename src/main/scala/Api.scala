package com.github.scalaApi
import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

import java.time.format.DateTimeFormatter



class Api extends Http4sDsl [IO]{
  private val printer =
    new TimePrinter(DateTimeFormatter.RFC_1123_DATE_TIME)

  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "ping" => Ok("pong")
    case GET -> Root / "datetime" / country =>
      try {
        Ok(printer.now(country))
      } catch {
        case ex:IllegalArgumentException => NotFound(ex.getMessage)
      }
  }
}
