package org.covid.vaccineman.service;

import org.covid.vaccineman.dto.user.UserCreateRequest;
import org.covid.vaccineman.dto.user.UserResponse;
import org.covid.vaccineman.dto.user.UserUpdateRequest;
import org.covid.vaccineman.entity.UserEntity;
import org.covid.vaccineman.exception.ResourceNotFoundException;
import org.covid.vaccineman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class UserService {

    private String[] tier1Pin;
    private String[] tier2Pin;
    private String[] adminUsers;

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${cities.tiers.tier1.pin.prefix}")
    public void setTier1Pin(String[] tier1Pin) {
        this.tier1Pin = tier1Pin;
    }

    @Value("${cities.tiers.tier2.pin.prefix}")
    public void setTier2Pin(String[] tier2Pin) {
        this.tier2Pin = tier2Pin;
    }

    @Value("${ADMIN_USERS}")
    public void setAdminUsers(String[] adminUsers) {
        this.adminUsers = adminUsers;
    }

    public void registerUser(UserCreateRequest user) {
        UserEntity response = userRepository.getUserEntityByChatIdAndPinCode(user.getChatId(), user.getPinCode());
        if(isEmpty(response)) {
            response = new UserEntity();
            response.setPinCode(user.getPinCode());
            response.setChatId(user.getChatId());
        }
        response.setAgeLimit(user.getAgeLimit());
        response.setIsOptedIn(TRUE);

        userRepository.save(response);
    }

    public List<String> getAllUsersList() throws ResourceNotFoundException {
        List<String> users = userRepository.getUsersList();
        if(isEmpty(users)) {
            throw new ResourceNotFoundException("No opted in users found");
        }

        return users;
    }

    public List<UserResponse> getAllActiveUsersByTierCities(Integer tier) throws ResourceNotFoundException {
        List<UserEntity> activeUsers = userRepository.getUserEntitiesByIsOptedInTrue();
        if(isEmpty(activeUsers)) {
            throw new ResourceNotFoundException("No opted in users found");
        }

        List<UserResponse> responses = enrichUserResponse(activeUsers);

        List<String> cityPinPrefixes = new ArrayList<>();
        cityPinPrefixes.addAll(Arrays.asList(tier1Pin));

        if(tier == 1) {
            return buildTierBasedUserResponse(cityPinPrefixes, responses, true);
        } else if(tier == 2) {
            cityPinPrefixes = Arrays.asList(tier2Pin);
            return buildTierBasedUserResponse(cityPinPrefixes, responses, true);
        }

        cityPinPrefixes.addAll(Arrays.asList(tier2Pin));

        return buildTierBasedUserResponse(cityPinPrefixes, responses, false);
    }

    public void optOutUser(UserUpdateRequest request) {
        if(isEmpty(request.getPinCode())) {
            userRepository.optOutUser(request.getChatId());
        } else {
            userRepository.optOutUser(request.getChatId(), request.getPinCode());
        }
    }

    public void optOutUsersWithPinCode(Integer pinCode) {
        userRepository.optOutUsersWithPinCode(pinCode);
    }

    public List<UserResponse> getAllAdminUsers() {
        List<UserEntity> activeUsers = userRepository.getUserEntitiesByIsOptedInTrueAndChatIdIn(Arrays.asList(adminUsers));

        return enrichUserResponse(activeUsers);
    }

    private List<UserResponse> enrichUserResponse(List<UserEntity> activeUsers) {
        List<UserResponse> responses = new ArrayList<>();
        Map<Integer, List<UserEntity>> pinCodeMap = activeUsers.stream().collect(Collectors.groupingBy(UserEntity::getPinCode));

        pinCodeMap.forEach((key, value) -> {
            List<UserResponse.Users> usersList = new ArrayList<>();
            value.stream().forEach(chat ->  {
                UserResponse.Users user = new UserResponse.Users();
                user.setChatId(chat.getChatId());
                user.setAgeLimit(chat.getAgeLimit());
                usersList.add(user);
            });
            UserResponse user = new UserResponse();
            user.setPinCode(key);
            user.setUsers((ArrayList<UserResponse.Users>) usersList);

            responses.add(user);
        });

        return responses;
    }

    private List<UserResponse> buildTierBasedUserResponse(List<String> cityPinPrefixes, List<UserResponse> response, boolean include) {
        List<UserResponse> responses = new ArrayList<>();

        if(include == false) {
            responses.addAll(response);
        }

        cityPinPrefixes.forEach(prefix ->
            response.stream().forEach(user -> {
                if (user.getPinCode().toString().startsWith(prefix)) {
                    if(include == true) {
                        responses.add(user);
                    } else {
                        responses.remove(user);
                    }
                }
            }));
        return responses;
    }
}
