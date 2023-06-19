FROM bellsoft/liberica-openjdk-alpine:11 as build
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src
RUN ./mvnw -B package

FROM bellsoft/liberica-openjdk-alpine:11
COPY --from=build /target/book_storage.jar /app/book_storage.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/book_storage.jar"]