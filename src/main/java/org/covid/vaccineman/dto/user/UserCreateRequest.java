package org.covid.vaccineman.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserCreateRequest {

    @JsonProperty("pin_code")
    @NotNull(message = "pin_code cannot be blank.")
    private Integer pinCode;

    @JsonProperty("chat_id")
    @NotBlank(message = "chat_id cannot be blank.")
    private String chatId;

    @JsonProperty("age_limit")
    private Integer ageLimit;

}
