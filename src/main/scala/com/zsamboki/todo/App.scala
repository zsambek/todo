package com.zsamboki.todo

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Resource, Timer}
import com.zsamboki.todo.config.{DatabaseConfig, TodoConfig}
import com.zsamboki.todo.domain.todos.TodoService
import com.zsamboki.todo.infrastructure.endpoint.TodoEndpoints
import com.zsamboki.todo.infrastructure.repository.doobie.TodoRepositoryInterpreter
import doobie.util.ExecutionContexts
import io.circe.config.parser
import org.http4s.server.{Router, Server}
import org.http4s.server.blaze.BlazeServerBuilder
import cats.syntax.functor._
import org.http4s.implicits._

object App extends IOApp {

  def createServer[F[_]: ContextShift: ConcurrentEffect: Timer]: Resource[F, Server[F]] =
    for {
      config <- Resource.liftF(parser.decodePathF[F, TodoConfig]("todo"))
      dbEc <- ExecutionContexts.fixedThreadPool[F](config.databaseConfig.poolSize)
      blockingEc <- ExecutionContexts.cachedThreadPool[F]
      xa <- DatabaseConfig.transactor(config.databaseConfig, dbEc, Blocker.liftExecutionContext(blockingEc))
      todoRepository = TodoRepositoryInterpreter(xa)
      todoService = TodoService(todoRepository)
      httpApp = Router("/" -> TodoEndpoints.endpoints(todoService)).orNotFound
      _ <- Resource.liftF(DatabaseConfig.init(config.databaseConfig))
      server <- BlazeServerBuilder[F]
        .bindHttp(config.serverConfig.port, config.serverConfig.host)
        .withHttpApp(httpApp)
        .resource
    } yield server

  override def run(args: List[String]): IO[ExitCode] = createServer.use(_ => IO.never).as(ExitCode.Success)
}
