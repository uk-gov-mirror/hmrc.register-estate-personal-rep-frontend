import sbt._

object AppDependencies {
  import play.core.PlayVersion

  val compile = Seq(
    play.sbt.PlayImport.ws,
    "org.reactivemongo"   %% "play2-reactivemongo"            % "0.18.8-play27",
    "uk.gov.hmrc"         %% "logback-json-logger"            % "4.8.0",
    "uk.gov.hmrc"         %% "govuk-template"                 % "5.61.0-play-27",
    "uk.gov.hmrc"         %% "play-health"                    % "3.16.0-play-27",
    "uk.gov.hmrc"         %% "play-ui"                        % "8.21.0-play-27",
    "uk.gov.hmrc"         %% "play-conditional-form-mapping"  % "1.5.0-play-27",
    "uk.gov.hmrc"         %% "bootstrap-frontend-play-27"     % "2.25.0",
    "com.typesafe.play"   %% "play-json-joda"                 % "2.7.4",
    "uk.gov.hmrc"         %% "domain"                         % "5.10.0-play-27",
    "uk.gov.hmrc"         %% "play-language"                  % "4.10.0-play-27"
  )

  val test = Seq(
    "org.scalatest"               %% "scalatest"              % "3.0.8",
    "org.scalatestplus.play"      %% "scalatestplus-play"     % "4.0.3",
    "org.pegdown"                 %  "pegdown"                % "1.6.0",
    "org.jsoup"                   %  "jsoup"                  % "1.12.1",
    "com.typesafe.play"           %% "play-test"              % PlayVersion.current,
    "org.mockito"                 %  "mockito-all"            % "1.10.19",
    "org.scalacheck"              %% "scalacheck"             % "1.14.3",
    "com.github.tomakehurst"      % "wiremock-standalone"     % "2.25.1",
    "wolfendale"                  %% "scalacheck-gen-regexp"  % "0.1.2"
  ).map(_ % Test)

  def apply(): Seq[ModuleID] = compile ++ test

  val akkaVersion = "2.6.7"
  val akkaHttpVersion = "10.1.12"

  val overrides = Seq(
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-protobuf" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion
  )
}
