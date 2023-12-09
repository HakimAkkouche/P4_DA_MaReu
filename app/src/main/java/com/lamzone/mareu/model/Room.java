package com.lamzone.mareu.model;

import androidx.annotation.NonNull;

public class Room {
    private final int idRoom;
    private final String roomName;
    String iconRes;
    String colorRes;
    /**
     * constructor
     * @param idRoom Identifier
     * @param roomName Room name
     */
    public Room(int idRoom, String roomName) {
        this.idRoom = idRoom;
        this.roomName = roomName;
        this.iconRes = "ic_".concat(roomName.toLowerCase());
        this.colorRes = roomName.toLowerCase();
    }

    /**
     * @return Room identifier
     */
    public int getIdRoom() {
        return idRoom;
    }
    /**
     * @return the name of the room
     */
    public String getRoomName() {
        return roomName;
    }
    /**
     * @return String drawable icon resource
     */
    public String getIconRes() {
        return iconRes;
    }

    /**
     * @return String color resource
     */
    public String getColorRes() {
        return colorRes;
    }
    /**
     * @return override toString to return RoomName
     */
    @NonNull
    @Override
    public String toString(){
        return roomName;
    }
}