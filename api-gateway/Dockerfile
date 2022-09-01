FROM openjdk:18-alpine
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api-gateway.jar
CMD ["java", "-jar", "api-gateway.jar"]