package com.github.maksimprivalov.subscriptions_telegrambot.command;

import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.TelegramUser;
import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageService;
import com.github.maksimprivalov.subscriptions_telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String START_MESSAGE = "Hi. I am Subscriptions Bot. You can use me to make sure you didn't miss any posts of your favourite pages. Let's start!";

    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },() -> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChatId(chatId);
                    telegramUserService.save(telegramUser);
                });

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
