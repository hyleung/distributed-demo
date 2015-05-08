#Inventory Service

A really shitty web service for returning (dummy) product availability.
 
By default, runs on port 3000.

Error rates, min/max latency configured on the main page [http://localhost:3000](http://localhost:3000/)

Web service "api" can be accessed via http GET to: 
[http://localhost:3000/api/availability/{someid}](http://localhost:3000/api/availability/{someid})

Obviously since this is a fake service, it doesn't matter what is passed to the service.

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server


## Tests

Uses [Midje](https://github.com/marick/Midje) for testing. Run it in the Leiningen repl:

    lein repl
    
    (use 'midje.repl)
    
    (autotest)
    
## Docker

The build includes the [lein-uberimage](https://github.com/palletops/lein-uberimage) plugin, which will build a Docker
image that can be used to run the app.

    lein uberimage
    
To create a tagged image:

    lein uberimage -t user/imagename:version
    
Refer to the `lein-uberimage` Github project for all the options.