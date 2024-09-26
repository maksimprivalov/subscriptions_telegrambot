package com.github.maksimprivalov.subscriptions_telegrambot.javarushclient.dto;
import lombok.Data;

@Data
public class MeGroupInfo {
    private MeGroupInfoStatus status;
    private Integer userGroupId;
}
