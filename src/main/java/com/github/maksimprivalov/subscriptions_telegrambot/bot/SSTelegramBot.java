package com.github.maksimprivalov.subscriptions_telegrambot.bot;
import com.github.maksimprivalov.subscriptions_telegrambot.command.CommandContainer;
import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Component;
import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandName.NO;

@Component
public class SSTelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    public SSTelegramBot() {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText().trim();
            if(message.startsWith(COMMAND_PREFIX)){
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            }else{
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }else{
            commandContainer.retrieveCommand("Sorry, i can't support this type of communication...").execute(update);
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