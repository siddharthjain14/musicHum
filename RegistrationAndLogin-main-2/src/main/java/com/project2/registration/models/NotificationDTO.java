package com.project2.registration.models;

public class NotificationDTO {
    private String email;
    private String notificationToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "email='" + email + '\'' +
                ", notificationToken='" + notificationToken + '\'' +
                '}';
    }
}
