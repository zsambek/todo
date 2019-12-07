package com.zsamboki.todo.domain.todos

case class Todo(id: Option[Long], name: String, status: TodoStatus = TodoStatus.NotStarted)
