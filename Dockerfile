FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=subscriptions_tg_bot
ENV BOT_TOKEN=7026490842:AAE9K-ooBZ3bzhq6DMbbO8Vyh8W5T_1tppg
ENV BOT_DB_USERNAME=root
ENV BOT_DB_PASSWORD=root
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]