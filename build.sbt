ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.0"

enablePlugins(JmhPlugin)

lazy val root = (project in file("."))
  .settings(
    name := "PortfolioAnalysis_Scala_OOP",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test,

    // JMH dependencies
    libraryDependencies +="org.openjdk.jmh" % "jmh-core" % "1.37",
    libraryDependencies += "org.openjdk.jmh" % "jmh-generator-annprocess" % "1.37"
  )
