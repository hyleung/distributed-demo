#Inventory Service (in Scala)

Shitty (fake) inventory service written in Play.

By default, runs on port 9000.

Error rate, min/max latency configured on the main page [http://localhost:9000](http://localhost:9000).

Service "api" accessible via http GET at [http://localhost:9000/inventory/{someId}](http://localhost:9000/inventory/foo).

Just a dummy service, so doesn't really matter what is passed to the service.

##Running

    sbt run

##Docker

Uses [sbt-native-packager](https://github.com/sbt/sbt-native-packager) to build a Docker image for the app.

    sbt docker:publishLocal

