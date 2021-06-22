# use a mminimalist image as root
FROM openjdk:15-jdk-alpine

ADD target/api.jar app.jar

# deploy project
CMD ["/bin/sh", "-c", ". java -jar /app.jar"]