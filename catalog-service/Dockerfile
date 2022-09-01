FROM openjdk:18-alpine
EXPOSE 9200
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} catalog-service.jar
CMD ["java", "-jar", "catalog-service.jar"]