package com.github.maksimprivalov.subscriptions_telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SubscriptionsTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionsTelegramBotApplication.class, args);
	}

}
