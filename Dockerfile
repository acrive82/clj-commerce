FROM openjdk:8-alpine

COPY target/uberjar/clj-commerce.jar /clj-commerce/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/clj-commerce/app.jar"]
