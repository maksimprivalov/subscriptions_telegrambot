package com.github.maksimprivalov.subscriptions_telegrambot.javarushclient;

import com.github.maksimprivalov.subscriptions_telegrambot.javarushclient.dto.PostInfo;

import java.util.List;

public interface JavaRushPostClient {
    List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId);
}
