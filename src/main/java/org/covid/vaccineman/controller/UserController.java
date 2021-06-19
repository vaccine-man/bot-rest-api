package org.covid.vaccineman.controller;

import lombok.extern.slf4j.Slf4j;
import org.covid.vaccineman.dto.user.UserCreateRequest;
import org.covid.vaccineman.dto.user.UserResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.covid.vaccineman.constant.ApiConstants.PINCODES_API;
import static org.covid.vaccineman.constant.ApiConstants.TIER_API;
import static org.covid.vaccineman.constant.ApiConstants.USERS_API;
import static org.covid.vaccineman.constant.ApiConstants.US_API;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@RestController
@RequestMapping(USERS_API)
public class UserController {

    private UserService userService;
    private PinCodeService pinCodeService;

    UserController(UserService userService, PinCodeService pinCodeService) {
        this.userService = userService;
        this.pinCodeService = pinCodeService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserCreateRequest request) throws ResourceNotFoundException {
        log.info("received request {}", request.toString());

        pinCodeService.isPinCodeValid(request.getPinCode());
        userService.registerUser(request);

        return new ResponseEntity(request.getPinCode(), HttpStatus.CREATED);
    }

    @GetMapping(value = TIER_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getAllActiveUsers(@RequestParam(value = "tier", required = false)
                                                                                        Integer tier) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.getAllActiveUsersByTierCities(isEmpty(tier) ? 3 : tier), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllUsers() throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.getAllUsersList(), HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity optOutUser(@Valid @RequestBody UserUpdateRequest request) {
        userService.optOutUser(request);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = US_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getAdminUsers() {
        return new ResponseEntity<>(userService.getAllAdminUsers(), HttpStatus.OK);
    }

    @PutMapping(value = PINCODES_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity optOutUsersWithPinCode(@RequestParam("pin_code") Integer pinCode) {
        userService.optOutUsersWithPinCode(pinCode);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
