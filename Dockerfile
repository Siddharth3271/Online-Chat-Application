FROM maven:3.9.11-eclipse-temurin-21-jammy as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from-build /target/Online-Chat-Application-0.0.1-SNAPSHOT.jar Online-Chat-Application.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","Online-Chat-Application.jar"]