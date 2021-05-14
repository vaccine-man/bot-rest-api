package org.covid.vaccineman.controller;

import lombok.extern.slf4j.Slf4j;
import org.covid.vaccineman.dto.user.UserResponse;
import org.covid.vaccineman.dto.user.UserCreateRequest;
import org.covid.vaccineman.dto.user.UserUpdateRequest;
import org.covid.vaccineman.exception.ResourceNotFoundException;
import org.covid.vaccineman.service.PinCodeService;
import org.covid.vaccineman.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.covid.vaccineman.constant.ApiConstants.USERS_API;

@Slf4j
@RestController
public class UserController {

    private UserService userService;
    private PinCodeService pinCodeService;

    UserController(UserService userService, PinCodeService pinCodeService) {
        this.userService = userService;
        this.pinCodeService = pinCodeService;
    }

    @PostMapping(value = USERS_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserCreateRequest request) throws ResourceNotFoundException {
        log.info("received request {}", request.toString());

        pinCodeService.isPinCodeValid(request.getPinCode());
        userService.registerUser(request);

        return new ResponseEntity(request.getPinCode(), HttpStatus.CREATED);
    }

    @GetMapping(value = USERS_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getAllActiveUsers() throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.getAllActiveUsers(), HttpStatus.OK);
    }

    @PutMapping(value = USERS_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity optOutUser(@Valid @RequestBody UserUpdateRequest request) throws ResourceNotFoundException {
        userService.optOutUser(request);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
