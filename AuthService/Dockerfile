FROM openjdk:17
LABEL authors="saadahmedscse"
EXPOSE 8081
ADD target/vendor-voyage-auth-service.jar vendor-voyage-auth-service.jar
ENTRYPOINT ["java", "-jar", "/vendor-voyage-auth-service.jar"]