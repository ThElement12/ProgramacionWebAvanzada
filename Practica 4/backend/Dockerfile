FROM maven:3.8.3-jdk-11 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

MAINTAINER Robert Garcia <20171086@ce.pucmm.edu.do>
# forma estandar para cambiar la configuracion
ENV DB_NAME=mocki_to
# Puedo pasar las variables directas que serán tomadas por Spring Boot.
ENV spring.datasource.username='sa'
ENV spring.datasource.password='1234'
VOLUME /tmp
FROM openjdk:11.0.11-slim

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/app.jar /app/

ENTRYPOINT ["java", "-jar", "app.jar"]
