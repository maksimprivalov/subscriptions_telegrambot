package com.github.maksimprivalov.subscriptions_telegrambot.bot;
import com.github.maksimprivalov.subscriptions_telegrambot.command.CommandContainer;
import com.github.maksimprivalov.subscriptions_telegrambot.javarushclient.JavaRushGroupClient;
import com.github.maksimprivalov.subscriptions_telegrambot.service.GroupSubService;
import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageServiceImpl;
import com.github.maksimprivalov.subscriptions_telegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandName.NO;
import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandUtils.getUsername;

@Component
public class SSTelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    @Autowired
    public SSTelegramBot(TelegramUserService telegramUserService, JavaRushGroupClient groupClient, GroupSubService groupSubService, @Value("#{'${bot.admins}'.split(',')}") List<String> admins) {
        this.commandContainer =
                new CommandContainer(new SendBotMessageServiceImpl(this),
                        telegramUserService, groupClient, groupSubService, admins);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText().trim();
            if(message.startsWith(COMMAND_PREFIX)){
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier, getUsername(update)).execute(update);
            }else{
                commandContainer.retrieveCommand(NO.getCommandName(), getUsername(update)).execute(update);
            }
        }else{
            commandContainer.retrieveCommand("Sorry, i can't support this type of communication...", getUsername(update)).execute(update);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

}