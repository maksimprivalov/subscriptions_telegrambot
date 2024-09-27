package com.github.maksimprivalov.subscriptions_telegrambot.command;

import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.TelegramUser;
import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageService;
import com.github.maksimprivalov.subscriptions_telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;

import java.util.stream.Collectors;

import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandUtils.getChatId;

public class ListGroupSubCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public ListGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        TelegramUser telegramUser = telegramUserService.findByChatId(getChatId(update)).orElseThrow(NotFoundException::new);
        String message = "I found all of your subscriptions: \n\n";
        String collectedGroups = telegramUser.getGroupSubs().stream()
                .map(group -> String.format("%s - %s\n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());
        sendBotMessageService.sendMessage(getChatId(update), message + collectedGroups);
    }
}
