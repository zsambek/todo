package com.zsamboki.todo.domain

sealed trait Errors extends Product with Serializable
case object NotFoundTodo extends Errors
