package com.zsamboki.todo.domain.todos

trait TodoRepositoryAlgebra[F[_]] {

  def create(todo: Todo): F[Todo]

  def get(id: Long): F[Option[Todo]]

  def delete(id: Long): F[Option[Todo]]

}
