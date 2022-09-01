FROM openjdk:18-alpine
EXPOSE 9300
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} user-service.jar
CMD ["java", "-jar", "user-service.jar"]