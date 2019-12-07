package com.zsamboki.todo.infrastructure.endpoint

import cats.effect.Sync
import cats.implicits._
import com.zsamboki.todo.domain.NotFoundTodo
import com.zsamboki.todo.domain.todos.{Todo, TodoService}
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

class TodoEndpoints[F[_]: Sync] extends Http4sDsl[F] {

  implicit val todoDecoder: EntityDecoder[F, Todo] = jsonOf[F, Todo]

  private def createTodoEndpoint(todoService: TodoService[F]) = HttpRoutes.of[F] {
    case request @ POST -> Root =>
      for {
        todo <- request.as[Todo]
        created <- todoService.create(todo)
        response <- Ok(created.asJson)
      } yield response
  }

  private def getTodoEndpoint(todoService: TodoService[F]) = HttpRoutes.of[F] {
    case GET -> Root / LongVar(id) =>
      todoService.get(id).value.flatMap {
        case Right(todo) => Ok(todo.asJson)
        case Left(NotFoundTodo) => NotFound("Todo was not found!")
      }
  }

  private def removeTodoEndpoint(todoService: TodoService[F]) = HttpRoutes.of[F] {
    case DELETE -> Root / LongVar(id) =>
      todoService.remove(id).value.flatMap {
        case Right(todo) => Ok(todo.asJson)
        case Left(NotFoundTodo) => NotFound("Todo was not found!")
      }
  }

  def endpoints(todoService: TodoService[F]): HttpRoutes[F] =
    createTodoEndpoint(todoService) <+> getTodoEndpoint(todoService) <+> removeTodoEndpoint(todoService)

}

object TodoEndpoints {

  def endpoints[F[_]: Sync](todoService: TodoService[F]): HttpRoutes[F] =
    new TodoEndpoints[F].endpoints(todoService)

}
