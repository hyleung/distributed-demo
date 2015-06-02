FROM jeanblanchard/busybox-java:8 
MAINTAINER Ho Yan Leung <hy.leung@gmail.com>
ENV REFRESHED_AT 2015-06-01

RUN mkdir -p /opt/app

ADD build/libs/api-gateway.jar /opt/app/

WORKDIR /opt/app

ENTRYPOINT ["java","-jar","api-gateway.jar"]
