libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.15.0"
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
scalacOptions += "-feature"
