package com.hyperspc.volunteercallbell;

/*
Call Object to be appended to Firebase
 */

public class Call {

    private int roomNum;
    private String timeStamp;
    private boolean status;

    public Call(int roomNumber, String timeCalled, boolean callStatus){
        roomNum = roomNumber;
        timeStamp = timeCalled;
        status = callStatus;
    }

    public int getRoomNumber(){
        return roomNum;
    }
    public String getTimeStamp(){
        return timeStamp;
    }
    public boolean getStatus(){
        return status;
    }
    public void setRoomNumber(int roomNumber){
        roomNum = roomNumber;
    }
    public void setTimeStamp(String timeCalled){
        timeStamp = timeCalled;
    }
    public void setStatus(boolean callStatus){
        status = callStatus;
    }

}
