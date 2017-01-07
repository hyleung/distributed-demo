FROM hyleung/busybox-java:latest
MAINTAINER Ho Yan Leung <hy.leung@gmail.com>
ENV REFRESHED_AT 2015-06-01

ENV STORE_INVENTORY_HOST http://storeinventory
ENV INVENTORY_HOST http://inventory

RUN mkdir -p /opt/app

ADD build/libs/catalog-service.jar /opt/app/
ADD scripts/startup.sh /opt/app/

WORKDIR /opt/app

EXPOSE 9999

ENTRYPOINT ["/bin/bash","startup.sh"]
