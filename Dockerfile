FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY key.json .
COPY ${JAR_FILE} pdfsplit-data-api.jar
ENTRYPOINT ["java","-jar","/pdfsplit-data-api.jar"]
EXPOSE 8000
