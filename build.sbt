val demoAkkaCluster = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning, JavaAppPackaging, DockerPlugin)

organization := "de.heikoseeberger"
name         := "demo-akka-cluster"
licenses     += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion   := "2.11.8"
scalacOptions ++= Vector(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

unmanagedSourceDirectories.in(Compile) := Vector(scalaSource.in(Compile).value)
unmanagedSourceDirectories.in(Test)    := Vector(scalaSource.in(Test).value)

val akkaVersion       = "2.4.4"
val constructrVersion = "0.12.0"
libraryDependencies ++= Vector(
  "com.typesafe.akka"        %% "akka-cluster"                 % akkaVersion,
  "com.typesafe.akka"        %% "akka-http-experimental"       % akkaVersion,
  "de.heikoseeberger"        %% "akka-log4j"                   % "1.1.3",
  "de.heikoseeberger"        %% "constructr-akka"              % "0.12.0",
  "de.heikoseeberger"        %% "constructr-coordination-etcd" % "0.12.0",
  "org.apache.logging.log4j" %  "log4j-core"                   % "2.5"
)

initialCommands := """|import de.heikoseeberger.demoakkacluster._""".stripMargin

git.useGitDescribe := true

import scalariform.formatter.preferences._
scalariformPreferences := scalariformPreferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)

headers := Map("scala" -> de.heikoseeberger.sbtheader.license.Apache2_0("2015", "Heiko Seeberger"))

daemonUser.in(Docker) := "root"
maintainer.in(Docker) := "Heiko Seeberger"
dockerBaseImage      := "java:8"
dockerExposedPorts   := Vector(2552, 8000)
dockerRepository     := Some("hseeberger")
