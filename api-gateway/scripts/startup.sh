#!/bin/bash

echo "Starting $STORE_INVENTORY_HOST"
echo "Starting $INVENTORY_HOST"

java -Xdebug -Xrunjdwp:transport=dt_socket,address=9999,server=y,suspend=n -Dinventory.service.host=$INVENTORY_HOST -Davailability.service.host=$STORE_INVENTORY_HOST -jar api-gateway.jar