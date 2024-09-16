git pull
mvn clean
mvn package
docker-compose stop
export BOT_NAME=$1
export BOT_TOKEN=$2
export PATH=/path/to/apache-maven-3.9.8/bin:$PATH
docker-compose up --build -d
