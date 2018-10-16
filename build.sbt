name := "sales-processor"

version := "0.1"

// https://mvnrepository.com/artifact/org.junit/junit5-engine
libraryDependencies += "org.junit.jupiter" % "junit-jupiter-engine" % "5.0.0-M4"
libraryDependencies += "org.junit.platform" % "junit-platform-runner" % "1.0.0-M4" % Test

crossPaths := false

libraryDependencies += "com.novocode" % "junit-interface" % "0.11"