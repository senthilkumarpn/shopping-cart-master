FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY web-module/target/web-module-1.0-SNAPSHOT.war web-module-1.0-SNAPSHOT.war

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/web-module-1.0-SNAPSHOT.war"]
