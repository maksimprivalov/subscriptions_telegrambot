package com.github.maksimprivalov.subscriptions_telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandUtils {
    public static String getChatId(Update update){
        return String.valueOf(update.getMessage().getChatId());
    }
    public static String getMessage(Update update){
        return update.getMessage().getText();
    }
}
