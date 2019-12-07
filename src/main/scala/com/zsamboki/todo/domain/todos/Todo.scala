package com.zsamboki.todo.domain.todos

case class Todo(id: Long, name: String, status: TodoStatus = TodoStatus.NotStarted)
