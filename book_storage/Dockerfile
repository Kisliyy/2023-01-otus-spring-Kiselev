FROM bellsoft/liberica-openjdk-alpine:11
COPY /target/book_storage.jar /app/book_storage.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/book_storage.jar"]