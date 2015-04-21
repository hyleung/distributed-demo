import NativePackagerKeys._

name := """inventory-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
    .enablePlugins(PlayScala, DockerPlugin)


scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

dockerBaseImage := "java:8"

dockerExposedPorts := Seq(9000)

maintainer := "H.Y.Leung <hy.leung@gmail.com>"