package com.zsamboki.todo.config
import cats.effect.{Async, Blocker, ContextShift, Resource, Sync}
import cats.syntax.functor._
import doobie.hikari.HikariTransactor
import org.flywaydb.core.Flyway
import io.circe.Decoder
import io.circe.generic.semiauto._

import scala.concurrent.ExecutionContext

case class DatabaseConfig(
    url: String,
    driver: String,
    user: String,
    password: String,
    poolSize: Int,
)

object DatabaseConfig {

  def transactor[F[_]: Async: ContextShift](
      config: DatabaseConfig,
      ec: ExecutionContext,
      blocker: Blocker,
  ): Resource[F, HikariTransactor[F]] =
    HikariTransactor.newHikariTransactor(config.driver, config.url, config.user, config.password, ec, blocker)

  def init[F[_]](config: DatabaseConfig)(implicit S: Sync[F]): F[Unit] =
    S.delay(
        Flyway
          .configure()
          .dataSource(config.url, config.user, config.password)
          .load()
          .migrate(),
      )
      .as(())

  implicit val databaseConfigDecoder: Decoder[DatabaseConfig] = deriveDecoder

}
