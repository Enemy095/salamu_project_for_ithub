FROM gradle:7.6-jdk17 as build
WORKDIR /app

COPY . /app

RUN gradle clean build -x test

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/build/libs/ithub-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
