package com.project2.registration.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project2.registration.entity.*;
import com.project2.registration.models.*;
import com.project2.registration.repository.CorporateRepository;
import com.project2.registration.repository.RegistrationRepository;
import com.project2.registration.services.LoginHistoryService;
import com.project2.registration.services.RegistrationService;
import com.project2.registration.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    LoginHistoryService loginHistoryService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    Pattern pattern = Pattern.compile(regex);

    //todo : phani : split this method to smaller function and each one dealing with one domain specific logic
    @Override
    public UserData addUser(UserDTO userDTO) {
        String code;
        List<Users> userExistList =  registrationRepository.findByEmail(userDTO.getEmail());

        Users  checkEmailExist =  registrationRepository.findByChannelIdAndEmail(userDTO.getChannelId(),userDTO.getEmail());
        Users  checkUsernameExist = registrationRepository.findByChannelIdAndUsername(userDTO.getChannelId(),userDTO.getUsername());

        UserData userData = new UserData();
        userData.setProfileImage("-1");
        userData.setUserId("-1");
        userData.setUsername("-1");

        if(!(checkEmailExist == null)) {
            userData.setCode(100);
            return userData;
        }else if (!(checkUsernameExist == null)){
            userData.setCode(101);
            return userData;
        }else if(userDTO.getPassword() == null){
            userData.setCode(102);
            return userData;
        }else if(!(pattern.matcher(userDTO.getEmail()).matches())){
            userData.setCode(103);
            return userData;
        }else {
            if(userExistList.size() == 0){
                userDTO.setUserId(randomStringGenerator());
            }else {
                Users userExist = userExistList.get(0);
                 {
                     userDTO.setUserId(userExist.getUserId());
                }
            }
            Users users = new Users();
            users.setName(userDTO.getName());
            users.setDateOfBirth(userDTO.getDateOfBirth());
            users.setBio(userDTO.getBio());
            users.setUserId(userDTO.getUserId());
            users.setMobileNumber(userDTO.getMobileNumber());
            users.setUsername(userDTO.getUsername());
            users.setAreaOfInterests(userDTO.getAreaOfInterests());
            users.setProfileImage(userDTO.getProfileImage());
            users.setType(userDTO.getType());
            users.setMaster(userDTO.isMaster());
            users.setNotificationToken(userDTO.getNotificationToken());
            users.setChannelId(userDTO.getChannelId());
            users.setEmail(userDTO.getEmail());
            users.setPassword(userDTO.getPassword());
            users.setTimeStamp(java.time.LocalDateTime.now());
//            users.setTimeStamp(userDTO.getTimeStamp());
            registrationRepository.save(users);
            if(users.getType() == 3){
                AdDTO addto=new AdDTO();
                addto.setCategory(users.getName());
                addto.setTitle(users.getName());
                addto.setDescription(users.getBio());
                addto.setImageUrl(users.getProfileImage());
                ObjectMapper objectMapper = new ObjectMapper();
                String serialObject = "";
                try {
                    serialObject = objectMapper.writeValueAsString(addto);
                    kafkaTemplate.send("Ads",serialObject);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }


            ObjectMapper objectMapper = new ObjectMapper();
            AnalyticsDto analyticsDto = new AnalyticsDto();
            analyticsDto.setUserId(users.getUserId());
            analyticsDto.setAreaOfInterests(users.getAreaOfInterests());
            analyticsDto.setChannelId(users.getChannelId());
            analyticsDto.setAction("register");
            String serialObject = "";
            try {
                serialObject = objectMapper.writeValueAsString(analyticsDto);
                kafkaTemplate.send("login",serialObject);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            LoginHistory loginHistory = new LoginHistory();
            loginHistory.setUserId(users.getUserId());
            loginHistory.setChannelId(users.getChannelId());
            loginHistory.setUserName(users.getUsername());
            loginHistory.setTimestamp(java.time.LocalDateTime.now());

            loginHistoryService.save(loginHistory);
            String jwt = jwtUtil.generateToken(users);
            userData.setJwt(jwt);
            return userData;
        }
    }

    //todo : split the method into domain specific methods and call them in the main logic
    @Override
    public ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest) throws Exception {
        if(authenticationRequest.getOauth()!=-1 && (findByChannelIdAndEmail(authenticationRequest.getOauth(),authenticationRequest.getEmail())!=null)
                &&(findByChannelIdAndEmail(authenticationRequest.getChannelId(),authenticationRequest.getEmail())==null)){
            Users userToSave = findByChannelIdAndEmail(authenticationRequest.getOauth(), authenticationRequest.getEmail());
            Users userFinal = new Users();
            userFinal.setChannelId(authenticationRequest.getChannelId());
            userFinal.setUserId(userToSave.getUserId());
            userFinal.setEmail(userToSave.getEmail());
            userFinal.setPassword(userToSave.getPassword());
            saveUser(userFinal);


            System.out.println("if exit");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail()+"*"+authenticationRequest.getChannelId(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect email or password", e);
        }


        Users users = findByChannelIdAndEmail(authenticationRequest.getChannelId(),authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(users);

        ObjectMapper objectMapper = new ObjectMapper();
        AnalyticsDto analyticsDto = new AnalyticsDto();
        analyticsDto.setUserId(users.getUserId());
        analyticsDto.setAreaOfInterests(users.getAreaOfInterests());
        analyticsDto.setChannelId(users.getChannelId());
        analyticsDto.setAction("login");
        String serialObject = "";
        try {
            serialObject = objectMapper.writeValueAsString(analyticsDto);
            kafkaTemplate.send("login",serialObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUserId(users.getUserId());
        loginHistory.setChannelId(users.getChannelId());
        loginHistory.setUserName(users.getUsername());
        loginHistory.setTimestamp(java.time.LocalDateTime.now());

        loginHistoryService.save(loginHistory);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    @Override
    public NotificationDTO sendNotification(int channelId, String userId) {
        System.out.println("in");
        //todo : use spring bean utils to copy properties
        Users users =  registrationRepository.findByChannelIdAndUserId(channelId, userId);
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(users.getEmail());
        notificationDTO.setNotificationToken(users.getNotificationToken());
        notificationDTO.toString();
        System.out.println("out");
        return notificationDTO;

    }

    @Override
    public void appendNotification(String notificationToken, int channelId, String userId) {
        Users users = registrationRepository.findByChannelIdAndUserId(channelId,userId);
        users.setNotificationToken(users.getNotificationToken()+ ","+notificationToken);
        registrationRepository.save(users);
        }

    @Override
    public UserData findByChannelIdAndEmailAndPassword(int channelId, String email, String password) {
        Users user = registrationRepository.findByChannelIdAndEmailAndPassword(channelId,email,password);
        UserData userLoginData = new UserData();
        if(user == null){
            userLoginData.setProfileImage("-1");
            userLoginData.setUserId("-1");
            userLoginData.setUsername("-1");
        }
        else{
            userLoginData.setProfileImage(user.getProfileImage());
            userLoginData.setUserId(user.getUserId());
            userLoginData.setUsername(user.getUsername());
        }

        //todo : user spring beanutils for copying properties .. move this code into a specific method
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUserId(user.getUserId());
        loginHistory.setChannelId(user.getChannelId());
        loginHistory.setUserName(user.getUsername());
        loginHistory.setTimestamp(java.time.LocalDateTime.now());
        loginHistoryService.save(loginHistory);

        return userLoginData;

    }

    @Override
    public UserDTO updateData(UserDTO userDTO) {
        Users users = registrationRepository.findByChannelIdAndEmail(userDTO.getChannelId(),userDTO.getEmail());
        ///To do waiting for token and oauth sorting
        if(userDTO.getBio()!=null){
            users.setBio(userDTO.getBio());
        }
        if(userDTO.getDateOfBirth()!=null){
            users.setDateOfBirth((userDTO.getDateOfBirth()));
        }
        if(userDTO.getMobileNumber()!= 0){
            users.setMobileNumber((userDTO.getMobileNumber()));
        }
        if(userDTO.getName()!=null){
            users.setName((userDTO.getName()));
        }
        if(userDTO.getUsername()!=null){
            users.setUsername((userDTO.getUsername()));
        }
        if(userDTO.getProfileImage()!=null){
            users.setProfileImage((userDTO.getProfileImage()));
        }
        if(userDTO.getType()<= 0){
            users.setType(userDTO.getType());
        }

        registrationRepository.save(users);
        return userDTO;
    }

    @Override
    public UserData loginMaster(int channelId, String email, String password) {
        Users user = registrationRepository.findByChannelIdAndEmailAndPassword(channelId, email, password);
        UserData userLoginData = new UserData();
        if (user.isMaster()) {
            if (user == null) {
                userLoginData.setProfileImage("-1");
                userLoginData.setUserId("-1");
                userLoginData.setUsername("-1");
            } else {
                userLoginData.setProfileImage(user.getProfileImage());
                userLoginData.setUserId(user.getUserId());
                userLoginData.setUsername(user.getUsername());
            }
        }
            return userLoginData;

    }

    public String randomStringGenerator() {
        String generatedString= UUID.randomUUID().toString();
        return generatedString;
    }

    @Override
    public Users findByChannelIdAndEmail(int channelId, String email) {
        return registrationRepository.findByChannelIdAndEmail(channelId, email);
    }

    @Override
    public Users saveUser(Users userFinal) {
        return registrationRepository.save(userFinal);
    }

    @Override
    public Users findByChannelIdAndUserId(int channelId, String username) {
        Users users = registrationRepository.findByChannelIdAndUsername(channelId, username);
        if(!(users == null)){
            users.setPassword("");
            users.setMaster(false);
            return users;
        }else {
            Users users1 = new Users();
            users1.setUserId("-1");
            return users1;
        }


    }
}
