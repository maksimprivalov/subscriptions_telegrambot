package com.github.maksimprivalov.subscriptions_telegrambot.repository;

import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, String> {
    List<TelegramUser> findAllByActiveTrue();

}
