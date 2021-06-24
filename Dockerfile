# use a mminimalist image as root
FROM openjdk:15-jdk-alpine

RUN addgroup -S immo && adduser -S immo -G immo

USER immo

WORKDIR /home/immo

ADD target/api.jar app.jar

# deploy project
CMD ["/bin/sh", "-c", "java -jar /home/immo/app.jar"]
