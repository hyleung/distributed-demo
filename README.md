#Distributed Demo

##Getting Started

This demo consists of 4 web applications:

- a catalog service (Java - using [Jetty](http://jetty.codehaus.org/) with [Jersey](http://jersey.java.net/))
- an inventory service (Clojure w/[Luminus](http://www.luminusweb.net))
- a "in-store" inventory service (Scala - using [Play Framework](http://playframework.com/))
- a [Hystrix](https://github.com/Netflix/Hystrix) Dashboard (Jetty)

###Building and running the web apps

####Start the Catalog service

    cd catalog-service
    gradle run 

The service will start by default on port 8080.

####Start the inventory service

    cd inventory-service
    lein ring server

The service will start by default on port 3000.

####Start the "in-store" inventory service

    cd store-inventory-service
    sbt run

The service will start by default on port 9000.

####Start the Hystrix dashboard

    cd dashboard
    gradle jettyRun

The dashboard should be available on port 7979.

###Buidling and running using Docker

    ./docker_build.sh
    docker-compose up

The ports (8080, 9000, 3000, 7979) should all be published.

##TL;DR

The general gist of this demo…we have a commerce-y website. Displays the product listings and product details (price, description, etc.)
In addition to this basic information, we also surface information regarding the avaiability of the product - whether the product is currently
"In Stock" as well as whether the product is availabile in local ("bricks-and-mortar") stores.

The data driving the site is provided by a product catalog service. Some of the data returned from the catalog service is sourced from the service's own "database" directly. 
Information pertaining to availability is obtained via API calls to other services.

The availability information is really just a "nice-to-have" bit of functionality. Users still need to be able to browse the product listings and (though
it's not implemneted) presumeably be able to place orders, etc. 

##Scenario: View Product Catalog

This scenario is fairly uninteresting - _all_ the data presented here is sourced directly from the catalog service. We *do*, however, want this feature to 
be as available as possible.

![Product Catalog](images/product_catalog.png)

##Scenario: View Product Detail

This is where the availability ("In Stock", "In-Store") information is presented. Again, this data is obtained via API call to other services. As with the Product 
Catalog scenario - we want the user to be able to view product info (and Add-To-Cart), *even if* we aren't able to obtain current availability information.

![Product Detail](images/product_detail.png)

##Hystrix

//ToDo
