FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY . .

RUN mvn -f pom.xml clean install -DskipTests

RUN mvn -pl service-discovery -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/service-discovery/target/service-discovery-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
