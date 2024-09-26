package com.github.maksimprivalov.subscriptions_telegrambot.repository;

import com.github.maksimprivalov.subscriptions_telegrambot.repository.entity.GroupSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupSubRepository extends JpaRepository<GroupSub, Integer> {
}
