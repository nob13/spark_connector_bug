ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

lazy val root = (project in file("."))
  .settings(
    name := "spark_bug_subelement"
  )

val sparkVersion = "3.3.2"
val connectorVersion = "3.3.0"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-network-common" % sparkVersion,
  "com.datastax.spark" %% "spark-cassandra-connector" % connectorVersion,
  "joda-time" % "joda-time" % "2.12.2"
)