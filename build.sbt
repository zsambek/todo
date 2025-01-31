organization := "com.zsamboki"
name := "todo"

version := "0.1"

scalaVersion := "2.12.10"

val CatsV = "2.0.0"
val CirceV = "0.12.3"
val EnumeratumCirceV = "1.5.22"
val DoobieV = "0.8.6"
val KindProjectorV = "0.10.3"
val Http4s = "0.21.0-M6"
val ParadiseV = "2.1.0"
val FlywayV = "6.1.0"
val LogbackV = "1.2.3"
val CirceConfigV = "0.7.0"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % CatsV,
  "io.circe" %% "circe-generic" % CirceV,
  "io.circe" %% "circe-literal" % CirceV,
  "com.beachape" %% "enumeratum-circe" % EnumeratumCirceV,
  "org.tpolecat" %% "doobie-core" % DoobieV,
  "org.tpolecat" %% "doobie-h2" % DoobieV,
  "org.tpolecat" %% "doobie-scalatest" % DoobieV,
  "org.tpolecat" %% "doobie-hikari" % DoobieV,
  "org.http4s" %% "http4s-blaze-server" % Http4s,
  "org.http4s" %% "http4s-circe" % Http4s,
  "org.http4s" %% "http4s-dsl" % Http4s,
  "ch.qos.logback" % "logback-classic" % LogbackV,
  "org.flywaydb" % "flyway-core" % FlywayV,
  "io.circe" %% "circe-config" % CirceConfigV
)

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
  "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
  "-Xlint:unsound-match", // Pattern match may not be typesafe.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xfuture", // Turn on future language features.
  "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  "-Ypartial-unification", // Enable partial unification in type constructor inference
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
)

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin(
  "org.typelevel" %% "kind-projector" % KindProjectorV
)
addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
)