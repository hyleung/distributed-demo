name := "load-test"

version := "1.0"

enablePlugins(GatlingPlugin)

scalaVersion := "2.11.6"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.5" % "test"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "2.1.5" % "test"
    
