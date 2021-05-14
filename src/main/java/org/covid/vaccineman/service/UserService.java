package org.covid.vaccineman.service;

import org.covid.vaccineman.dto.user.UserResponse;
import org.covid.vaccineman.dto.user.UserCreateRequest;
import org.covid.vaccineman.dto.user.UserUpdateRequest;
import org.covid.vaccineman.entity.UserEntity;
import org.covid.vaccineman.exception.ResourceNotFoundException;
import org.covid.vaccineman.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserCreateRequest user) {
        UserEntity response = userRepository.getUserWithPin(user.getChatId(), user.getPinCode());
        if(isEmpty(response)) {
            response = new UserEntity();
            response.setPinCode(user.getPinCode());
            response.setChatId(user.getChatId());
        }
        response.setIsOptedIn(TRUE);

        userRepository.save(response);
    }

    public List<UserResponse> getAllActiveUsers() throws ResourceNotFoundException {
        List<UserEntity> activeUsers = userRepository.getAllActiveUsers();
        if(isEmpty(activeUsers)) {
            throw new ResourceNotFoundException("No opted in users found");
        }

        List<UserResponse> responses = new ArrayList<>();
        Map<Integer, List<UserEntity>> pinCodeMap = activeUsers.stream().collect(Collectors.groupingBy(UserEntity::getPinCode));

        pinCodeMap.forEach((key, value) -> {
            List<String> chatIdList = new ArrayList<>();
            value.stream().forEach(chat -> chatIdList.add(chat.getChatId()));
            UserResponse user = new UserResponse();
            user.setPinCode(key);
            user.setChatId((ArrayList<String>) chatIdList);

            responses.add(user);
        });

        return responses;
    }

    public void optOutUser(UserUpdateRequest request) throws ResourceNotFoundException {
        userRepository.optOutUser(request.getChatId());
    }
}
