#!/bin/bash

cd store-inventory-service
sbt docker:publishLocal

cd ../inventory-service
lein uberimage -t inventory-service:latest

cd ../catalog-service

gradle clean jar
docker build -t catalog-service:latest .

cd ../dashboard
gradle clean jar
docker build -t hystrix-dashboard:latest .

cd ..
