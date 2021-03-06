name := """sojourninghere_twitter"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5"

libraryDependencies += "com.google.inject" % "guice" % "3.0"

libraryDependencies += "javax.inject" % "javax.inject" % "1"
