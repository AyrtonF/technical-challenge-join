FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn install -e -X

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/product-catalog-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080


CMD ["java", "-jar", "app.jar"]
