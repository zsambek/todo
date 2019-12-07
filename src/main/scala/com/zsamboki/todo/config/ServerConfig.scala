package com.zsamboki.todo.config
import io.circe.Decoder
import io.circe.generic.semiauto._

case class ServerConfig(host: String, port: Int)

object ServerConfig {

  implicit val serverConfigDecoder: Decoder[ServerConfig] = deriveDecoder

}
