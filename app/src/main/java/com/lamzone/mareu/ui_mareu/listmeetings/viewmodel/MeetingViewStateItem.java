package com.lamzone.mareu.ui_mareu.listmeetings.viewmodel;

import androidx.annotation.NonNull;

import com.lamzone.mareu.model.Participant;
import com.lamzone.mareu.model.Room;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * MeetingViewStateItem
 * @author Hakim
 */
public class MeetingViewStateItem {
    private final int meetingId;
    private final String meetingName;
    private final LocalDateTime startTimeDate;
    private final Room room;
    private final List<Participant> participants;


    public MeetingViewStateItem(int meetingId,
                                String meetingName,
                                LocalDateTime startTimeDate,
                                Room room,
                                List<Participant> participants) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.startTimeDate = startTimeDate;
        this.room = room;
        this.participants = participants;
    }

    /**
     * Meeting id
     */
    public int getMeetingId() {
        return meetingId;
    }
    /**
     * Room
     */
    public Room getRoom() {
        return room;
    }
    /**
     * Meeting name
     */
    public String getMeetingName() {
        return meetingName;
    }
    /**
     * Date and hour of the meeting
     */
    public LocalDateTime getStartTimeDate() {
        return startTimeDate;
    }
    /**
     * List of participants
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewStateItem that = (MeetingViewStateItem) o;
        return Objects.equals(meetingName, that.meetingName)
                && startTimeDate.equals(that.startTimeDate)
                && room == that.room
                && participants.equals(that.participants);
    }
    @Override
    public int hashCode() {
        return Objects.hash(meetingName,room, startTimeDate);
    }
    /**
     * return a string of the details of the meeting
     * @return String
     */
    @NonNull
    public String toString(){
        return meetingName +
                " - " +
                room.getRoomName() +
                "\n" +
                startTimeDate.format(DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm"));
    }
    /**
     * Return the list of participants formatted to a string separated with a ";"
     * @return String
     */
    public String getParticipantsToString(){
        StringBuilder s = new StringBuilder();
        for (Participant participant : participants) {
            s.append(participant.getEmailParticipant())
                    .append(";");
        }
        return s.toString();
    }
}
