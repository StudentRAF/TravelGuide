
FROM maven:3.9.7-eclipse-temurin-21 AS build
ARG VERSION="1.0-SNAPSHOT"
WORKDIR /usr/app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests
ADD ./target/TravelGuide-${VERSION}.war ./target/TravelGuide.war

FROM tomcat:10.1.24-jre21-temurin
COPY --from=build /usr/app/target/TravelGuide.war /usr/local/tomcat/webapps/
