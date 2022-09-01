FROM openjdk:18-alpine
EXPOSE 9500
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} book-service.jar
CMD ["java", "-jar", "book-service.jar"]