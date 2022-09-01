FROM openjdk:18-alpine
EXPOSE 9400
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} studyhall-service.jar
CMD ["java", "-jar", "studyhall-service.jar"]