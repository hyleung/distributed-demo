#!/bin/bash

cd store-inventory-service
sbt docker:publishLocal

cd ../inventory-service
lein uberimage -t inventory-service:latest

cd ../api-gateway

gradle clean jar
docker build -t api-gateway:latest .

cd ../dashboard
gradle clean jar
docker build -t hystrix-dashboard:latest .

cd ..
