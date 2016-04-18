scalaVersion := "2.11.7"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")
    scalacOptions := Seq(
      "-unchecked", "-deprecation", "-encoding", "utf8", "-feature",
      "-language:existentials", "-language:implicitConversions",
      "-language:reflectiveCalls", "-target:jvm-1.8"
    )
//resolvers += Resolver.bintrayRepo("scalawilliam", "actionfps")
resolvers += Resolver.url("my", url("https://dl.bintray.com/scalawilliam/actionfps"))(Resolver.ivyStylePatterns)

libraryDependencies += "com.actionfps" %% "accumulation" % "5.3.0"

run in Compile <<= (run in Compile).dependsOn(downloadAll)

lazy val downloadAll = taskKey[Unit]("Download the games")

downloadAll := {
  if ( !file("all.tsv").exists() ) {
    IO.download(url("https://actionfps.com/all/"), file("all.tsv"))
  }
}
