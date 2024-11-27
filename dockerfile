# Используем базовый образ JDK 22 на базе Alpine Linux для этапа сборки.
FROM eclipse-temurin:22-jdk-alpine as build
    # Устанавливаем рабочую директорию внутри контейнера.
   WORKDIR /workspace/app

    # Копируем скрипт mvnw (для выполнения Maven без предварительной установки Maven на системе).
   COPY mvnw .
   # Копируем каталог .mvn, который содержит файлы конфигурации Maven Wrapper.
   COPY .mvn .mvn
   # Копируем файл pom.xml, который содержит информацию о проекте и зависимости.
   COPY pom.xml .
   # Копируем весь исходный код из папки src в контейнер.
   COPY src src

   # Выполняем сборку Maven, пропуская тесты (флаг -DskipTests).
   RUN ./mvnw install -DskipTests
   # Создаем директорию target/dependency и извлекаем содержимое собранного JAR-файла в нее.
   RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
   # Используем другой базовый образ JRE 22 на базе Alpine Linux для этапа выполнения.
   FROM eclipse-temurin:22-jre-alpine
   # Определяем, что контейнер будет использовать временный том для хранения временных данных.
   VOLUME /tmp
   # Задаем переменную ARG, указывающую на директорию зависимостей из предыдущего этапа.
   ARG DEPENDENCY=/workspace/app/target/dependency
   # Копируем зависимости (библиотеки) из папки BOOT-INF/lib предыдущего этапа в директорию /app/lib.
   COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
   # Копируем метаданные приложения (файлы в META-INF) из предыдущего этапа в /app/META-INF.
   COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
   # Копируем скомпилированные классы приложения из папки BOOT-INF/classes в /app.
   COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

   # Устанавливаем переменные окружения для конфигурации подключения к базе данных MySQL.
   # Эти переменные используются приложением Spring Boot.
   ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:4444/bookstore
   ENV SPRING_DATASOURCE_USERNAME=${DB_USERNAME}
   ENV SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
    # Определяем команду для запуска приложения.
    # - "java" — запускает JVM.
    # - "-cp" — указывает путь к классам и зависимостям.
    # - "app:app/lib/*" — добавляет директории /app и все JAR-файлы из /app/lib в classpath.
    # - "bookstore.javabrightbrains.JavaBrightBrainsApplication" — главный класс приложения.
   ENTRYPOINT ["java","-cp","app:app/lib/*","bookstore.javabrightbrains.JavaBrightBrainsApplication"]