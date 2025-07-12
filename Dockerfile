FROM maven:3.9.6-amazoncorretto-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17.0.13
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
