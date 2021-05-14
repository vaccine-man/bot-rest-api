package org.covid.vaccineman.repository;

import org.covid.vaccineman.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Component
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u from UserEntity u WHERE u.chatId=?1 AND u.pinCode=?2")
    UserEntity getUserWithPin(String chatId, Integer pinCode);

    @Query("SELECT u FROM UserEntity u WHERE u.isOptedIn=true")
    List<UserEntity> getAllActiveUsers();

    @Modifying
    @Query("UPDATE UserEntity u SET u.isOptedIn=false WHERE u.chatId=?1")
    @Transactional
    void optOutUser(String chatId);
}
