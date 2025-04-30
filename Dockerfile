###### Building stage
FROM gradle:8.13.0-jdk21 AS build_stage

# Add whole source code .
ADD . /src

# Select working directory.
WORKDIR /src

# Build out the application's JAR file(s).
RUN ./gradlew build

###### Running stage
FROM eclipse-temurin:21 AS running_stage

# Set the timezone environment variable.
ENV TZ=Asia/Ho_Chi_Minh

# Create directories for application's built artifact and config.
RUN mkdir -p /opt/app /opt/app/config

# Create non-root user to run the application with user privilege.
RUN groupadd -r spring && useradd -r -g spring spring -m -s /bin/bash
USER spring:spring

# Copy app config for PROD env
COPY --from=build_stage /src/build/resources/main/application-prod.yml /opt/app/config

# Copy app's built artifact for PROD env
COPY --from=build_stage /src/build/libs/vulpes-0.0.1-SNAPSHOT.jar /opt/app

# Set the working directory
WORKDIR /opt/app

# Run the application
CMD ["java", "-jar", "/opt/app/vulpes-0.0.1-SNAPSHOT.jar"]
