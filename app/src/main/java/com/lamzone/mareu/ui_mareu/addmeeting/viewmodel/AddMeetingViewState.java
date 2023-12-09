package com.lamzone.mareu.ui_mareu.addmeeting.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lamzone.mareu.model.Participant;
import com.lamzone.mareu.model.Room;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AddMeetingViewState
 * @author Hakim
 */
public class AddMeetingViewState {
    /**
     * Rooms available
     */
    @Nullable
    private final List<Room> mRoomsAvailable;
    /**
     * Start date time of the meeting
     */
    @NonNull
    private final LocalDateTime mStartTime;
    /**
     * List of participants
     */
    @Nullable
    private final List<Participant> mParticipants;
    /**
     * Error of meeting Name input
     */
    @Nullable
    private final String mMeetingNameError;
    /**
     * room selected
     */
    @Nullable
    private final Room mRoomSelected;
    /**
     * Error of meeting room input
     */
    @Nullable
    private final String mRoomError;
    /**
     * Error of meeting participant input
     */
    @Nullable
    private final String mParticipantError;

    /**
     * Constructor
     * @param roomsAvailable rooms available
     * @param startTime date and hour of the meeting
     * @param participants list of participants
     * @param meetingNameErrorError meeting edittext error
     * @param roomSelected room chosen
     * @param roomError room (not) selected error
     * @param participantError participants error
     */
    public AddMeetingViewState(
            @Nullable List<Room> roomsAvailable, @NonNull LocalDateTime startTime,
            @Nullable List<Participant> participants, @Nullable String meetingNameErrorError,
            @Nullable Room roomSelected, @Nullable String roomError, @Nullable String participantError
    ) {
        this.mRoomsAvailable = roomsAvailable;
        this.mRoomSelected = roomSelected;
        this.mStartTime = startTime;
        this.mParticipants = participants;
        this.mMeetingNameError = meetingNameErrorError;
        this.mRoomError = roomError;
        this.mParticipantError = participantError;
    }
    /**
     * @return rooms available of the selected date and time
     */
    @Nullable
    public List<Room> getRoomsAvailable() {
        return mRoomsAvailable;
    }
    /**
     * @return date and time selected
     */
    @NonNull
    public LocalDateTime getStartTime() {
        return mStartTime;
    }
    /**
     * @return Meeting input error
     */
    @Nullable
    public String getMeetingNameError() {
        return mMeetingNameError;
    }
    /**
     * @return room input error
     */
    @Nullable
    public String getRoomError() {
            return mRoomError;
        }
    /**
     * @return participant input error
     */
    @Nullable
    public String getParticipantError() {
        return mParticipantError;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddMeetingViewState that = (AddMeetingViewState) o;
        if(mRoomSelected != null || that.mRoomSelected != null
        ||mParticipants != null || that.mParticipants != null) {
            return (mRoomSelected.equals(that.mRoomSelected)
                    && mParticipants.equals(that.mParticipants)
                    && mStartTime.equals(that.mStartTime));
        }
        return false;
    }
}
