package com.github.maksimprivalov.subscriptions_telegrambot.command;

import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public final static String START_MESSAGE = "Hi. I am Subscriptions Bot. You can use me to make sure you didn't miss any posts of your favourite pages. Let's start!";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),START_MESSAGE);
    }
}
