package com.project2.registration.controller;

import com.project2.registration.entity.*;
import com.project2.registration.models.AuthenticationRequest;
import com.project2.registration.models.NotificationDTO;
import com.project2.registration.models.UserDTO;
import com.project2.registration.services.LoginHistoryService;
import com.project2.registration.services.RegistrationService;
import com.project2.registration.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
//todo : split the controller based on the domain models like login / registration, moderator add, followers add etc..  do the same for the services layer as well
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    LoginHistoryService loginHistoryService;




    @PostMapping("save")
    UserData addUser(@RequestBody UserDTO userDTO){
        return registrationService.addUser(userDTO);
    }

    /*@GetMapping("login/{channelId}/{email}/{password}")
    UserData findByEmailAndPassword(@PathVariable int channelId, @PathVariable String email, @PathVariable String password){
        return registrationService.findByChannelIdAndEmailAndPassword(channelId,email,password);
    }*/

    @PutMapping("update")
    UserDTO updateData(@RequestBody UserDTO userDTO){
        return registrationService.updateData(userDTO);
    }

    @GetMapping("login/master/{channelId}/{email}/{password}")
    UserData loginMaster(@PathVariable int channelId, @PathVariable String email, @PathVariable String password){
        return registrationService.loginMaster(channelId,email,password);
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
          return registrationService.createAuthenticationToken(authenticationRequest);
    }

    @GetMapping("/findByChannelIdAndUsername/{channelId}/{username}")
    public Users findByChannelIdAndUsername(@PathVariable int channelId,@PathVariable String username){
        return registrationService.findByChannelIdAndUserId(channelId,username);
    }

    @GetMapping("notification/{channelId}/{userId}")
    public NotificationDTO findByChannelIdAndUserId(@PathVariable int channelId, @PathVariable String userId){
        System.out.println("");
        return registrationService.sendNotification(channelId,userId);
    }

    @PostMapping("appendNotification/{channelId}/{userId}")
    public void getNotification(@RequestBody String notificationToken,@PathVariable int channelId, @PathVariable String userId){
        registrationService.appendNotification(notificationToken,channelId,userId);
    }
}
