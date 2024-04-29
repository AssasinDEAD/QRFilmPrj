FROM eclipse-temurin:21.02.2_13-jre-jammy
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "*/app.jar"]