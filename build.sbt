val demoAkkaCluster = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)

organization := "de.heikoseeberger"
name         := "demo-akka-cluster"
licenses     += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion   := "2.11.7"
scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

unmanagedSourceDirectories.in(Compile) := List(scalaSource.in(Compile).value)
unmanagedSourceDirectories.in(Test)    := List(scalaSource.in(Test).value)

libraryDependencies ++= List(
  "com.typesafe.akka" %% "akka-cluster"           % "2.4.0",
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0"
)

initialCommands := """|import de.heikoseeberger.demoakkacluster._""".stripMargin

git.baseVersion := "0.1.0"

import scalariform.formatter.preferences._
preferences := preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)

headers := Map("scala" -> de.heikoseeberger.sbtheader.license.Apache2_0("2015", "Heiko Seeberger"))
