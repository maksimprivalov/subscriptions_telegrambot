FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=subscriptions_tg_bot
ENV BOT_TOKEN=7026490842:AAFFG5M3WLTf5EmkmLtl6NMZHafHm6x7yqM
ENV BOT_DB_USERNAME=jrtb_db_user
ENV BOT_DB_PASSWORD=jrtb_db_password
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]