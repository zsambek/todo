package com.zsamboki.todo.domain.todos

import cats.Functor
import cats.data.EitherT
import com.zsamboki.todo.domain.NotFoundTodo

class TodoService[F[_]](todoRepository: TodoRepositoryAlgebra[F]) {

  def create(todo: Todo): F[Todo] =
    todoRepository.create(todo)

  def get(id: Long)(implicit F: Functor[F]): EitherT[F, NotFoundTodo.type, Todo] =
    EitherT.fromOptionF(todoRepository.get(id), NotFoundTodo)

  def remove(id: Long)(implicit F: Functor[F]): EitherT[F, NotFoundTodo.type, Todo] = {
    EitherT.fromOptionF(todoRepository.remove(id), NotFoundTodo)
  }

}

object TodoService {

  def apply[F[_]](todoRepository: TodoRepositoryAlgebra[F]): TodoService[F] = {
    new TodoService(todoRepository)
  }

}