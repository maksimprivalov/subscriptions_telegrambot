package com.github.maksimprivalov.subscriptions_telegrambot.service;

public interface SendBotMessageService {

    void sendMessage(String chatId, String message);
}
