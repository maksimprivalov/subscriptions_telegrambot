mvn clean
mvn package
docker-compose stop
export BOT_NAME=$1
export BOT_TOKEN=$2
export BOT_DB_USERNAME='tg_bot_user'
export BOT_DB_PASSWORD='tg_bot_password'
export PATH=/path/to/apache-maven-3.9.8/bin:$PATH
docker-compose up --build -d
