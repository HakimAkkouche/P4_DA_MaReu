package com.lamzone.mareu.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class Meeting
 * @author Hakim
 */
public class Meeting {
    private final int mMeetingId;
    private final String mMeetingName;
    private final LocalDateTime mStartTimeDate;
    private final Room mRoom;
    private final List<Participant> mParticipants;

    /**
     * Meeting constructor
     * @param meetingId meeting unique identifier
     * @param meetingName meeting name
     * @param startTimeDate start date and time of the meeting
     * @param room room of the meeting
     * @param participants list of participants
     */
    public Meeting(int meetingId,
                   String meetingName,
                   LocalDateTime startTimeDate,
                   Room room,
                   List<Participant> participants) {
        this.mMeetingId = meetingId;
        this.mMeetingName = meetingName;
        this.mStartTimeDate = startTimeDate;
        this.mRoom = room;
        this.mParticipants = participants;
    }
    /**
     * getMeetingId
     * @return Meeting unique identifier
     */
    public int getMeetingId() {
        return mMeetingId;
    }
    /**
     * getRoom
     * @return Room object
     */
    public Room getRoom() {
        return mRoom;
    }
    /**
     * getMeetingName
     * @return String Meeting Name
     */
    public String getMeetingName() {
        return mMeetingName;
    }
    /**
     * @return the start Localdatetime of the meeting
     */
    public LocalDateTime getStartTimeDate() {
        return mStartTimeDate;
    }

    /**
     * @return list of Participant object
     */
    public List<Participant> getParticipants() {
        return mParticipants;
    }
}
