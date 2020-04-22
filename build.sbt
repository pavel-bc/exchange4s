import sbt._
import sbt.Keys._
import sbt.Defaults._
import Dependencies._

lazy val Examples = config("example") extend Compile

lazy val commons = Seq(
  organization := "com.blockchain.exchange4s",

  homepage := Some(new URL("https://github.com/pavel-bc/blockchain4s")),

  startYear := Some(2020),

  organizationName := "Blockchain",

  scalaVersion := "2.13.0",

  version := "1.0.0",

  licenses := Seq(("MIT", url("https://opensource.org/licenses/MIT"))),

  scmInfo := Some(
    ScmInfo(
      url("https://github.com/pavel-bc/exchange4s"),
      "scm:git@github.com:pavel-bc/exchange4s.git")),

  developers := List(
    Developer(
      id    = "pavel-bc",
      name  = "Pavel Kiselyov",
      email = "pavel@blockchain.com",
      url   = url("https://www.linkedin.com/in/pavel-kiselyov"))),
)

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

lazy val root = (project in file("."))
  .configs(Examples)
  .settings(inConfig(Examples)(compileBase ++ compileSettings ++ Seq(
    run     := Defaults.runTask(fullClasspath in Examples, mainClass in run, runner in run).evaluated,
    runMain := Defaults.runMainTask(fullClasspath in Examples, runner in run).evaluated)))
  .settings(
    commons,
    name := "blockchain4s",
    libraryDependencies ++= all)