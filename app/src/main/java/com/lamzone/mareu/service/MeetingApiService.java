package com.lamzone.mareu.service;


import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface MeetingApiService
 * @author Hakim
 */
public interface MeetingApiService {

    /**
     * return List of meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();
    /**
     * Add a meeting
     * @param meeting Meeting
     */
    void addMeeting(Meeting meeting);

    /**
     * Delete a meeting
     * @param meetingId identifier
     */
    void deleteMeeting(int meetingId);
    /**
     * return list of rooms
     * @return {@link List}
     */
    List<Room> getRooms();
    /**
     * return list of available room for a datetime
     * @param startTime date and time
     * @return {@link List}
     */
    List<Room> getAvailableRooms(LocalDateTime startTime);

    /**
     *
     * @return int last id of the list for dummyApi
     */
    int getLastId();
}
