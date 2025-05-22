# 📬 Telegram Bot for Receiving New Articles from Russia's Top Java Forum
This Telegram bot automatically fetches and sends new articles from one of Russia's most popular Java forums.
## 🔧 Technical Overview
This project is a personal pet project built using:
- **Java Spring Boot**
- **Spring Data**
- Light use of **Spring Security**
- **Spring Scheduler** – for periodic article fetching via Swagger API
- **MySQL** – as the database
- **Docker** – for easy containerized deployment
## 🚀 Deployment
### Prerequisites
To deploy the bot, make sure you have the following installed:

- Bash-compatible terminal
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

to deploy application, switch to needed branch and run bash script:

$ bash start.sh ${bot_username} ${bot_token}

That's all.
