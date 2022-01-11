FROM maven:3.8.3-jdk-11 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package
#mantenimiento
MAINTAINER Robert Garcia <20171086@ce.pucmm.edu.do>

ENV spring.mail.host='smtp.gmail.com'
ENV spring.mail.port=587
ENV spring.mail.username='prestalialoans@gmail.com'
ENV spring.mail.password='gjjoxurzhbdbqfjp'

VOLUME /tmp
FROM openjdk:11.0.11-slim

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/app.jar /app/

ENTRYPOINT ["java", "-jar", "app.jar"]
