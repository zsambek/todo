package com.zsamboki.todo

import cats.effect.{Blocker, ExitCode, IO, IOApp, Resource}
import cats.syntax.functor._
import com.zsamboki.todo.config.{DatabaseConfig, TodoConfig}
import com.zsamboki.todo.domain.todos.TodoService
import com.zsamboki.todo.infrastructure.endpoint.TodoEndpoints
import com.zsamboki.todo.infrastructure.repository.TodoRepositoryInterpreter
import doobie.util.ExecutionContexts
import io.circe.config.parser
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.{Router, Server}

object App extends IOApp {

  def createServer: Resource[IO, Server[IO]] =
    for {
      config <- Resource.liftF(parser.decodePathF[IO, TodoConfig]("todo"))
      dbEc <- ExecutionContexts.fixedThreadPool[IO](config.databaseConfig.poolSize)
      blockingEc <- ExecutionContexts.cachedThreadPool[IO]
      xa <- DatabaseConfig.transactor[IO](config.databaseConfig, dbEc, Blocker.liftExecutionContext(blockingEc))
      todoRepository = TodoRepositoryInterpreter(xa)
      todoService = TodoService(todoRepository)
      httpApp = Router("/" -> TodoEndpoints.endpoints(todoService)).orNotFound
      _ <- Resource.liftF(DatabaseConfig.init[IO](config.databaseConfig))
      server <- BlazeServerBuilder[IO]
        .withNio2(true)
        .bindHttp(config.serverConfig.port, config.serverConfig.host)
        .withHttpApp(httpApp)
        .resource
    } yield server

  override def run(args: List[String]): IO[ExitCode] = createServer.use(_ => IO.never).as(ExitCode.Success)
}
