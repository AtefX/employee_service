FROM openjdk:17
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} employee-service.jar
ENTRYPOINT ["java","-jar","employee-service.jar"]