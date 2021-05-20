package org.covid.vaccineman.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserResponse {

    @JsonProperty("pin_code")
    private Integer pinCode;

    private ArrayList<Users> users;

    @Getter
    @Setter
    public static class Users {
        @JsonProperty("chat_id")
        private String chatId;

        @JsonProperty("age_limit")
        private Integer ageLimit;
    }
}
