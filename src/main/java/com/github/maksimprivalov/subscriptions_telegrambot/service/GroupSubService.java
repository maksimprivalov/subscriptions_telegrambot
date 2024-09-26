package com.github.maksimprivalov.subscriptions_telegrambot.service;

import com.github.maksimprivalov.subscriptions_telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.GroupSub;

public interface GroupSubService {
    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}
