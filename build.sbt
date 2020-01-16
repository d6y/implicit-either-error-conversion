name := "example"

version := "1.0.0"

scalaVersion := "2.13.1"

val cats = Seq("org.typelevel" %% "cats-core" % "2.0.0")

libraryDependencies ++= cats

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Ywarn-value-discard",
  "-Xlint",
  "-Xfatal-warnings",
)

