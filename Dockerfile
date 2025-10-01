#Basic Dockerfile
#FROM openjdk:21-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#EXPOSE 8082
#ENTRYPOINT ["java", "-jar", "/app.jar"]

#Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
# Set the working directory in the container
WORKDIR /app
COPY . .
# Build the application using Maven
RUN mvn clean package -DskipTests

#Run Stage
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copy the built JAR file from the build stage into the container
COPY --from=builder /app/target/todo-0.0.1-SNAPSHOT.jar app.jar
# Expose the port your application will run on
EXPOSE 8082
# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]