# Выбор официального образа OpenJDK
FROM eclipse-temurin:17-jre-focal AS base

# Установка рабочей директории
WORKDIR /app

# Кэширование зависимостей
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Экспозиция порта приложения
EXPOSE 8080

# Запуск приложения
CMD java -Djava.security.egd=file:/dev/./urandom -jar app.jar
