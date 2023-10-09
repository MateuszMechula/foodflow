FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/foodflow.jar app.jar
CMD ["java", "-jar", "app.jar"]

