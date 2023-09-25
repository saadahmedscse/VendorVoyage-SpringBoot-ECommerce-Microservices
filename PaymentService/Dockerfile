FROM openjdk:17
LABEL authors="saadahmedscse"
EXPOSE 8086
ADD target/vendor-voyage-payment-service.jar vendor-voyage-payment-service.jar
ENTRYPOINT ["java", "-jar", "/vendor-voyage-payment-service.jar"]