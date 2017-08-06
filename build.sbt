name := "skaryna"

version := "1.0"

scalaVersion := "2.11.11"

val sparkVersion = "2.2.0"
val scalatestVersion = "3.0.1"

libraryDependencies ++= Seq(
  "org.apache.spark"  %% "spark-core"       % sparkVersion,
  "org.apache.spark"  %% "spark-sql"        % sparkVersion,
  "org.apache.spark"  %% "spark-catalyst"   % sparkVersion,
  "org.scalatest"     %% "scalatest"        % scalatestVersion %  "test"
)
