package com.zsamboki.todo.domain

sealed trait Error extends Product with Serializable
case object NotFoundTodo extends Error
