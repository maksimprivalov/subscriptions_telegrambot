FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=subscriptions_tg_bot
ENV BOT_TOKEN=7026490842:AAFFG5M3WLTf5EmkmLtl6NMZHafHm6x7yqM
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]