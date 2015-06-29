package com.example.binyamin.fakebook;

/**
 * Created by binyamin on 27-May-15.
 */
public class Channel {
    private String icon;
    private String name;
    private String id;
    private Boolean isJoined;

    public Channel(String icon, String name, String id, Boolean isJoined) {
        this.icon = icon;
        this.name = name;
        this.id = id;
        this.isJoined = isJoined;
    }
    public Channel() {
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name= name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setIsJoined(Boolean isJoined) {
        this.isJoined = isJoined;
    }
    public int getIsJoined(){
        return isJoined?1:0;
    }
}
