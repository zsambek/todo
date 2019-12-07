package com.zsamboki.todo.config

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class TodoConfig(serverConfig: ServerConfig, databaseConfig: DatabaseConfig)

object TodoConfig {

  implicit val todoConfigDecoder: Decoder[TodoConfig] = deriveDecoder

}
