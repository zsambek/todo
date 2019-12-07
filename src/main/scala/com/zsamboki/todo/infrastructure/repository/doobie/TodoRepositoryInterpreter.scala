package com.zsamboki.todo.infrastructure.repository.doobie

import cats.data.OptionT
import cats.effect.Bracket
import cats.implicits._
import com.zsamboki.todo.domain.todos.{Todo, TodoRepositoryAlgebra, TodoStatus}
import doobie._
import doobie.implicits._
import doobie.util.Meta

private object Queries {

  implicit val todoStatusMeta: Meta[TodoStatus] =
    Meta[String].imap(TodoStatus.withName)(_.entryName)

  def select(id: Long): Query0[Todo] =
    sql"""
         SELECT id, name, status
         FROM todos
         WHERE id = $id
         """.query[Todo]
  def insert(todo: Todo): Update0 =
    sql"""
         INSERT INTO todos (name, status)
         VALUES (${todo.name}, ${todo.status})
         """.update

  def delete(id: Long): Update0 =
    sql"""
          DELETE FROM todos
          WHERE id = $id
          """.update

}

class TodoRepositoryInterpreter[F[_]: Bracket[*[_], Throwable]](val xa: Transactor[F])
    extends TodoRepositoryAlgebra[F] {

  def create(todo: Todo): F[Todo] =
    Queries
      .insert(todo)
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => todo.copy(id = id.some))
      .transact(xa)

  def get(id: Long): F[Option[Todo]] =
    Queries
      .select(id)
      .option
      .transact(xa)

  def remove(id: Long): F[Option[Todo]] =
    OptionT(get(id))
      .semiflatMap(todo => Queries.delete(id).run.transact(xa).as(todo))
      .value
}

object TodoRepositoryInterpreter {
  def apply[F[_]: Bracket[*[_], Throwable]](xa: Transactor[F]) = new TodoRepositoryInterpreter(xa)
}
