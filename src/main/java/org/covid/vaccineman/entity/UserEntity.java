package org.covid.vaccineman.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "user_info")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Column(name = "chat_id", updatable = false)
    private String chatId;

    @NotNull
    @Column(name = "pin_code", updatable = false)
    private Integer pinCode;

    @NotNull
    @Column(name = "user_active", columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isOptedIn;

    @Column(name = "age_limit")
    private Integer ageLimit;
}
