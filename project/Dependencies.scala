import sbt._

object Dependencies {
  lazy val Examples = config("example") extend Compile

  val circeVersion = "0.13.0"

  val websocketClient = "com.github.andyglow" %% "websocket-scala-client" % "0.3.0"      % Compile
  val circeCore       = "io.circe"            %% "circe-core"             % circeVersion % Compile
  val circeGeneric    = "io.circe"            %% "circe-generic"          % circeVersion % Compile
  val circeParser     = "io.circe"            %% "circe-parser"           % circeVersion % Compile

  val all = Seq(websocketClient, circeCore, circeGeneric, circeParser)
}
