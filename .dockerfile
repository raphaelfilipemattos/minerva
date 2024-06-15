FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY back/target/*.jar minerva.jar
ENTRYPOINT ["java","-jar","/minerva.jar"]