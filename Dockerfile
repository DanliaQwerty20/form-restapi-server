# Используем официальный образ OpenJDK в качестве базового
FROM openjdk:17-jdk-slim AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY . .

# Устанавливаем зависимости и собираем проект
RUN ./mvnw clean package -DskipTests

# Используем более легкий образ для финального контейнера
FROM openjdk:17-jre-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный jar-файл из этапа builder
COPY --from=builder /app/target/korchagin-0.0.1-SNAPSHOT.jar application.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "application.jar"]
