package com.lamzone.mareu.utils;

public class TransitionUtils {

    public static String getMeetingRoomTransitionName(int meetingId) {
        return "meeting_room" + meetingId;
    }

    public static String getMeetingDetailsTransitionName(int meetingId) {
        return "meeting_name" + meetingId;
    }
}