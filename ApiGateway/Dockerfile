FROM openjdk:17
LABEL authors="saadahmedscse"
EXPOSE 8080
ADD target/vendor-voyage-api-gateway.jar vendor-voyage-api-gateway.jar
ENTRYPOINT ["java", "-jar", "/vendor-voyage-api-gateway.jar"]