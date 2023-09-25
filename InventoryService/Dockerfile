FROM openjdk:17
LABEL authors="saadahmedscse"
EXPOSE 8083
ADD target/vendor-voyage-inventory-service.jar vendor-voyage-inventory-service.jar
ENTRYPOINT ["java", "-jar", "/vendor-voyage-inventory-service.jar"]