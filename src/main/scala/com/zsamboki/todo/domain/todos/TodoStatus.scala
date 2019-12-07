package com.zsamboki.todo.domain.todos

import enumeratum._

import scala.collection.immutable

sealed trait TodoStatus extends EnumEntry

case object TodoStatus extends Enum[TodoStatus] with CirceEnum[TodoStatus] {
  case object Started extends TodoStatus
  case object Finished extends TodoStatus
  case object NotStarted extends TodoStatus

  val values: immutable.IndexedSeq[TodoStatus] = findValues
}
