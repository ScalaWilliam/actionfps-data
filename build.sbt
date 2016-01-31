scalaVersion := "2.11.7"

//resolvers += Resolver.bintrayRepo("scalawilliam", "actionfps")
resolvers += Resolver.url("my", url("https://dl.bintray.com/scalawilliam/actionfps"))(Resolver.ivyStylePatterns)

libraryDependencies += "com.actionfps" %% "accumulation" % "5.1-1-g8a46e56"

run in Compile <<= (run in Compile).dependsOn(downloadAll)

lazy val downloadAll = taskKey[Unit]("Download the games")

downloadAll := {
  if ( !file("all.tsv").exists() ) {
    IO.download(url("https://actionfps.com/all/"), file("all.tsv"))
  }
}