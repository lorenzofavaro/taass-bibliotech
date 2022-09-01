FROM openjdk:18-alpine
EXPOSE 8761
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} eureka-server.jar
CMD ["java", "-jar", "eureka-server.jar"]