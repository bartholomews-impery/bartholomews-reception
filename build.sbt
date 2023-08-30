// As seen in:
// https://github.com/pbassiner/sbt-multi-project-example/blob/master/build.sbt
// https://medium.com/akka-scala/scala-3-create-an-sbt-project-with-subprojects-and-build-the-fat-jar-1b992643eb59

val scala3Version = "3.3.0"
val projectVersion = "0.1.0-SNAPSHOT"

lazy val root = project
  .in(file("."))
  .settings(
    name := "bartholomews-reception",
    version := projectVersion,

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

lazy val common = project
  .in(file("common"))
  .settings(
    name := "common",
    version := projectVersion,

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq()
  )

lazy val api = project
  .in(file("api"))
  .settings(
    name := "api",
    version := projectVersion,

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "io.nats" % "jnats" % "2.16.14",
      "com.google.code.gson" % "gson" % "2.10.1"
    )
  )
  .dependsOn(common)

lazy val service = project
  .in(file("service"))
  .settings(
    name := "service",
    version := projectVersion,

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "io.nats" % "jnats" % "2.16.14",
      "com.google.code.gson" % "gson" % "2.10.1"
    )
  )
  .dependsOn(common)
