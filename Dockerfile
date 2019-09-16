FROM openjdk:8
COPY . /usr/src/sales
WORKDIR /usr/src/sales
ADD build/libs/sales-service-docker-0.1.jar sales-service-docker-0.1.jar
EXPOSE 8102
VOLUME /tmp
ENTRYPOINT ["java","-jar","sales-service-docker-0.1.jar"]