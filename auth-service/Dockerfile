FROM openjdk:18-alpine
EXPOSE 9100
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} auth-service.jar
CMD ["java", "-jar", "auth-service.jar"]