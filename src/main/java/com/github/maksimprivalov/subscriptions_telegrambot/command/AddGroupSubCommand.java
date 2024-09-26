package com.github.maksimprivalov.subscriptions_telegrambot.command;

import com.github.maksimprivalov.subscriptions_telegrambot.javarushclient.JavaRushGroupClient;
import com.github.maksimprivalov.subscriptions_telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.maksimprivalov.subscriptions_telegrambot.javarushclient.dto.GroupRequestArgs;
import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.GroupSub;
import com.github.maksimprivalov.subscriptions_telegrambot.service.GroupSubService;
import com.github.maksimprivalov.subscriptions_telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandName.ADD_GROUP_SUB;
import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandUtils.getChatId;
import static com.github.maksimprivalov.subscriptions_telegrambot.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class AddGroupSubCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService, JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if(getMessage(update).equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())){
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        String chatId = getChatId(update);
        if (isNumeric(groupId)){
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())){
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "You're successfully followed the group " + "\"" + savedGroupSub.getTitle() + "\"");
        }else{
            sendGroupNotFound(chatId, groupId);
        }
    }
    private void sendGroupIdList(String chatId){
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "To follow the group type: \n " +
                "/addGroupSub \"group id\" (eg. /addGroupSub 16\n" +
                "All the groups are here:\n" +
                "name - id\n\n" +
                "%s";
        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }

    private void sendGroupNotFound(String chatId, String groupId){
        String groupNotFoundMessage = "There is no group with ID \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }
}
