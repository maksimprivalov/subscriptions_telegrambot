package com.github.maksimprivalov.subscriptions_telegrambot.command;

import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandName.*;


public class HelpCommand implements  Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Available commands</b>✨\n\n"
    + "<b> Start \\ stop working with bot</b>\n" +
            "%s - start working with me ✨\n" +
            "%s - pause working with me\n\n" +
            "Subscriptions: \n" +
            "%s - subscribe a group \n" +
            "%s - check my subscriptions \n\n" +
            "%s - get some helpful manuals about working with me!\n", START.getCommandName(), STOP.getCommandName(),
            ADD_GROUP_SUB.getCommandName(), LIST_GROUP_SUB.getCommandName(), HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
