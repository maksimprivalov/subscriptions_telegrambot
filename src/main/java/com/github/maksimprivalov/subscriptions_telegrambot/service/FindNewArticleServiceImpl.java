package com.github.maksimprivalov.subscriptions_telegrambot.service;

import com.github.maksimprivalov.subscriptions_telegrambot.javarushclient.JavaRushPostClient;
import com.github.maksimprivalov.subscriptions_telegrambot.javarushclient.dto.PostInfo;
import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.GroupSub;
import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewArticleServiceImpl implements FindNewArticleService{

    public static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.com/groups/posts/%s";

    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendMessageService;

    public FindNewArticleServiceImpl(GroupSubService groupSubService, JavaRushPostClient javaRushPostClient, SendBotMessageService sendMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void findNewArticles() {
        groupSubService.findAll().forEach(gSub -> {
            List<PostInfo> newPosts = javaRushPostClient.findNewPosts(gSub.getId(), gSub.getLastArticleId());

            setNewLastArticleId(gSub, newPosts);

            notifySubscribersAboutNewArticles(gSub, newPosts);
        });
    }

    private void setNewLastArticleId(GroupSub gSub, List<PostInfo> newPosts) {
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    gSub.setLastArticleId(id);
                    groupSubService.save(gSub);
                });
    }

    private void notifySubscribersAboutNewArticles(GroupSub gSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewArticles = newPosts.stream()
                .map(post -> String.format("✨New article <b>%s</b> in group <b>%s</b>.✨\n\n" +
                                "<b>Description:</b> %s\n\n" +
                                "<b>Ref:</b> %s\n",
                        post.getTitle(), gSub.getTitle(), post.getDescription(), getPostUrl(post.getKey())))
                .collect(Collectors.toList());

        gSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it -> {
                    messagesWithNewArticles.forEach(message -> sendMessageService.sendMessage(it.getChatId(), message));
                });
    }

    private Object getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }

}
