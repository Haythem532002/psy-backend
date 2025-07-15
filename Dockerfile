FROM eclipse-temurin:21.0.2_13-jdk-jammy as builder

WORKDIR /app 

COPY .mvn/ .mvn 

COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src/ ./src

RUN ./mvnw clean install


FROM eclipse-temurin:21.0.2_13-jre-jammy as production

EXPOSE 7090

COPY --from=builder /app/target/*.jar /app/target/*.jar

ENTRYPOINT [ "java" , "-jar" , "/app/target/*.jar" ]



