FROM eclipse-temurin:21

EXPOSE 8080

ARG JAR_FILE=target/application-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} evoPlus.jar

ENTRYPOINT ["java","-jar","/evoPlus.jar"]