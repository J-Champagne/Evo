FROM eclipse-temurin:21

EXPOSE 8080

ARG JAR_FILE=target/evo_server-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} evoPlus.jar

ENTRYPOINT ["java","-jar","/evoPlus.jar"]