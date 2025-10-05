FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY ../ /app

RUN mvn -f /app/pom.xml clean install -DskipTests

RUN mvn -pl cart-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/cart-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
