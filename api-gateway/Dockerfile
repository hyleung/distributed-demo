FROM java:8 
MAINTAINER Ho Yan Leung <hy.leung@gmail.com>
ENV REFRESHED_AT 2015-05-31

RUN mkdir -p /opt/app

ADD build/libs/api-gateway.jar /opt/app/

WORKDIR /opt/app

ENTRYPOINT ["/usr/bin/java","-jar","api-gateway.jar"]