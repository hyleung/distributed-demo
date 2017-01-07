#!/bin/bash

java -Dinventory.service.host=$INVENTORY_HOST -Davailability.service.host=$STORE_INVENTORY_HOST -jar catalog-service.jar
