package com.project2.registration.models;

public class AnalyticsDto {
    private String userId;
    private int channelId;
    private String areaOfInterests;
    private String action;

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

    public String getAreaOfInterests() {
        return areaOfInterests;
    }

    public void setAreaOfInterests(String areaOfInterests) {
        this.areaOfInterests = areaOfInterests;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
