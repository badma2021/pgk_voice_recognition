FROM adoptopenjdk/openjdk11:alpine-jre
LABEL "name"="pgk"
ARG JAR_FILE=target/pgk-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENV DB_HOST=localhost
ENV DB_NAME=pgk
ENV DB_USER=root
ENV DB_PASSWORD=root
EXPOSE 8888
ENTRYPOINT ["java","-jar","app.jar"]
