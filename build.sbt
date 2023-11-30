scalaVersion := "2.13.10"
val chiselVersion = "6.0.0-M3"
addCompilerPlugin("org.chipsalliance" % "chisel-plugin" % chiselVersion cross CrossVersion.full)
libraryDependencies += "org.chipsalliance" %% "chisel" % chiselVersion
