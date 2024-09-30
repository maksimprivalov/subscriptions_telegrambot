package com.github.maksimprivalov.subscriptions_telegrambot.command;

import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandName.STAT;
import static java.lang.String.format;

public class AdminHelpCommand implements Command{

    public static final String ADMIN_HELP_MESSAGE = format("✨<b>Available commands for admin</b>✨\n\n"
                    + "<b>Get statistics</b>\n"
                    + "%s - bot statistics\n",
            STAT.getCommandName());

    private final SendBotMessageService sendBotMessageService;

    public AdminHelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), ADMIN_HELP_MESSAGE);
    }
}
