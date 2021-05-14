package org.covid.vaccineman.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserUpdateRequest {

    @JsonProperty("chat_id")
    @NotBlank(message = "chat_id cannot be blank")
    private String chatId;

}
