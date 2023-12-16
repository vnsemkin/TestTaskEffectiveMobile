# Use a base image with Java
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at the working directory
COPY target/tms.jar .

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "tms.jar"]
