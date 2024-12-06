FROM eclipse-temurin:22-jdk-alpine as build
   WORKDIR /workspace/app

   COPY mvnw .
   COPY .mvn .mvn
   COPY pom.xml .
   COPY src src

   RUN chmod +x ./mvnw
   RUN ./mvnw install -DskipTests

   RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

   FROM eclipse-temurin:22-jre-alpine

   VOLUME /tmp

   ARG DEPENDENCY=/workspace/app/target/dependency

   COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib

   COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF

   COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

   ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:4444/bookstore
   ENV SPRING_DATASOURCE_USERNAME=${DB_USERNAME}
   ENV SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}

   ENTRYPOINT ["java","-cp","app:app/lib/*","bookstore.javabrightbrains.JavaBrightBrainsApplication"]