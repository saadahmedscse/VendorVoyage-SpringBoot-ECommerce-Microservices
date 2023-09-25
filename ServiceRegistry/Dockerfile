FROM openjdk:17
LABEL authors="saadahmedscse"
EXPOSE 8761
ADD target/vendor-voyage-discovery-server.jar vendor-voyage-discovery-server.jar
ENTRYPOINT ["java", "-jar", "/vendor-voyage-discovery-server.jar"]