package com.github.maksimprivalov.subscriptions_telegrambot.command;

import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String STOP_MESSAGE = "I deactivated all your subscriptions \uD83D\uDE1F.";
    public StopCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }


    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), STOP_MESSAGE);
    }
}
