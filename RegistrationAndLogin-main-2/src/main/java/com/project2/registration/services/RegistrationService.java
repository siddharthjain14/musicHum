package com.project2.registration.services;

import com.project2.registration.entity.*;
import com.project2.registration.models.AuthenticationRequest;
import com.project2.registration.models.NotificationDTO;
import com.project2.registration.models.UserDTO;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {
    UserData addUser(UserDTO userDTO);

    UserData findByChannelIdAndEmailAndPassword(int channelId,String email, String password);

    UserDTO updateData(UserDTO userDTO);

    UserData loginMaster(int channelId, String email, String password);

    Users findByChannelIdAndEmail(int channelId, String email);

    Users saveUser(Users userFinal);

    Users findByChannelIdAndUserId(int channelId, String userId);

    ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest) throws Exception;

    NotificationDTO sendNotification(int channelId, String userId);

    void appendNotification(String notificationToken, int channelId, String userId);

}
