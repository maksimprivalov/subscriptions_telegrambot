package com.github.maksimprivalov.subscriptions_telegrambot.command;

import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.GroupSub;
import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.TelegramUser;
import com.github.maksimprivalov.subscriptions_telegrambot.service.GroupSubService;
import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageService;
import com.github.maksimprivalov.subscriptions_telegrambot.service.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandName.DELETE_GROUP_SUB;
import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandUtils.getChatId;
import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandUtils.getMessage;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class DeleteGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    public DeleteGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if(getMessage(update).equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())){
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        String chatId = getChatId(update);
        if(isNumeric(groupId)){
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.valueOf(groupId));
            if(optionalGroupSub.isPresent()){
                GroupSub groupSub = optionalGroupSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);
                sendBotMessageService.sendMessage(chatId, format("I removed the subscription on \"%s\" from your list!", groupSub.getTitle()));
            }else{
                sendBotMessageService.sendMessage(chatId, "Hmm.. I can't find the group with this ID :(");
            }
        }else{
            sendBotMessageService.sendMessage(chatId, "Irregular format\n" +
                    "ID should be a positive number");
        }
    }

    private void sendGroupIdList(String chatId){
        String message;
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getGroupSubs();
        if(groupSubs.isEmpty()){
            message = "You haven't subscribed on any group yet! " +
                    "\nType /addGroupSub and choose a group to subscribe!";
        }else{
            message = "To delete the subscription type th command with the group ID" +
                    "\nFor example, /deleteGroupSub 16 \n\n" +
                    "Here are your active subscriptions:\n" +
                    "Group - ID\n\n" +
                    "%s";
        }
        String userGroupData = groupSubs.stream()
                .map(group -> format("%s - %s\n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(chatId, format(message, userGroupData));
    }
}
