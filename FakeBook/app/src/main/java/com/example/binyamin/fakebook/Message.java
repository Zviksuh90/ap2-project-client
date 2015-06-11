package com.example.binyamin.fakebook;

public class Message {

    //private variables
    String channel;
    String text;
    String user;
    String date_time;
    String longitude;
    String latitude;

    // Empty constructor
    public Message(String channel, String text, String user, String date_time,
                   String longitude, String latitude) {
        this.channel=channel;
        this.text=text;
        this.user=user;
        this.date_time=date_time;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public Message() {

    }

    String getChanel(){
        return channel;
    }

    void setChannel(String channel){
        this.channel=channel;
    }

    String getText(){
        return text;
    }

    void setText(String text){
        this.text=text;
    }

    String getUser(){
        return user;
    }

    void setUser(String user){
        this.user=user;
    }

    String getDate_time(){
        return date_time;
    }

    void setDate_time(String date_time){
        this.date_time=date_time;
    }

    String getLongitude(){
        return longitude;
    }

    void setLongitude(String longitude){
        this.longitude=longitude;
    }

    String getLatitude(){
        return latitude;
    }

    void setLatitude(String latitude){
        this.latitude=latitude;
    }
}