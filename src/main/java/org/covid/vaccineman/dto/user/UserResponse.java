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

    @JsonProperty("chat_id")
    private ArrayList<String> chatId;
}
