package com.project2.registration.models;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class UserDTO {

    @Id
    private String userId;
    @Id
    private int channelId;
    private String name;
    private String username;
    private String email;
    private String dateOfBirth;
    private long mobileNumber;
    private String password;
    private String profileImage;
   // private boolean type; //public:false private:true
   // private boolean corporateEntity;
   private int type;
    private String areaOfInterests;
    private String bio;
    private boolean master;
    private String notificationToken;
    private LocalDateTime timeStamp;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

/*    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isCorporateEntity() {
        return corporateEntity;
    }

    public void setCorporateEntity(boolean corporateEntity) {
        this.corporateEntity = corporateEntity;
    }*/

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAreaOfInterests() {
        return areaOfInterests;
    }

    public void setAreaOfInterests(String areaOfInterests) {
        this.areaOfInterests = areaOfInterests;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }


    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
