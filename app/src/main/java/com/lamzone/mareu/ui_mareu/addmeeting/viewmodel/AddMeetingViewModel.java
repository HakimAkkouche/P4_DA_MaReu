package com.lamzone.mareu.ui_mareu.addmeeting.viewmodel;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lamzone.mareu.DI.DependencyInjector;
import com.lamzone.mareu.R;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Participant;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.service.MeetingApiService;
import com.lamzone.mareu.utils.livedata.SingleLiveEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class ViewModel for the addMeetingFragment
 * @author Hakim
 */
public class AddMeetingViewModel extends ViewModel {
    /**
     * Resources
     */
    @NonNull
    private final Resources mResources;
    /**
     * Meenting ApiService
     */
    @NonNull
    private final MeetingApiService mMeetingApiService = DependencyInjector.getInstance();
    /**
     *Meeting Name
     */
    @Nullable
    private String mMeetingName;
    /**
     * Time and date of the meeting
     */
    private LocalDateTime mStartTime = LocalDateTime.now().minusMinutes(LocalDateTime.now().getMinute());
    /**
     * Room of the meeting
     */
    @Nullable
    private Room mRoom;
    /**
     * List of Participants
     */
    @NonNull
    private List<Participant> mParticipants = new ArrayList<>();
    /**
     * Mutable Livedata of admeetingViewState
     */
    private final MutableLiveData<AddMeetingViewState> mAddMeetingViewStateMutableLiveData = new MutableLiveData<>();
    /**
     * SingleLiveEvent of AddMeetingViewAction
     */
    private final SingleLiveEvent<AddMeetingViewAction> mViewActionSingleLiveEvent = new SingleLiveEvent<>();
    /**
     * Constructor
     * @param resources resources
     */
    public AddMeetingViewModel(@NonNull Resources resources) {
        this.mResources = resources;

        mAddMeetingViewStateMutableLiveData.setValue(
                new AddMeetingViewState(
                        mMeetingApiService.getAvailableRooms(mStartTime),
                        mStartTime,
                        mParticipants, null,
                        mRoom, null,
                        null
                )
        );
    }
    /**
     * @return live LiveData of AddMeetingViewState
     */
    @NonNull
    public LiveData<AddMeetingViewState> getViewStateLiveData() {
        return mAddMeetingViewStateMutableLiveData;
    }

    /**
     * @return LiveData of AddMeetingViewAction
     */
    public LiveData<AddMeetingViewAction> getViewActionLiveData() {
        return mViewActionSingleLiveEvent;
    }
    /**
     * action to call on MeetingNameChanged
     * @param meetingName meenting name
     */
    public void onNameChanged(@NonNull String meetingName) {
        this.mMeetingName = meetingName;

        AddMeetingViewState currentViewState = mAddMeetingViewStateMutableLiveData.getValue();

        if (!meetingName.isEmpty() && currentViewState != null && currentViewState.getMeetingNameError() != null) {
            mAddMeetingViewStateMutableLiveData.setValue(
                    new AddMeetingViewState(
                            currentViewState.getRoomsAvailable(),
                            currentViewState.getStartTime(),
                            mParticipants, null,
                            mRoom, currentViewState.getRoomError(),
                            currentViewState.getParticipantError()
                    )
            );
        }
    }
    /**
     * action to call on Room changed
     * @param room room
     */
    public void onRoomChanged(@Nullable Room room) {
        this.mRoom = room;

        AddMeetingViewState currentViewState = mAddMeetingViewStateMutableLiveData.getValue();

        if (room != null && currentViewState != null && currentViewState.getRoomError() != null) {
            mAddMeetingViewStateMutableLiveData.setValue(
                    new AddMeetingViewState(
                            currentViewState.getRoomsAvailable(),
                            currentViewState.getStartTime(),
                            mParticipants, currentViewState.getMeetingNameError(),
                            mRoom, null,
                            currentViewState.getParticipantError()
                    )
            );
        }
    }
    /**
     * Action called on DateText clicked
     */
    public void onDateTimeClicked() {
        mViewActionSingleLiveEvent.setValue(new AddMeetingViewAction.DisplayDateTimePicker(mStartTime));
    }
    /**
     * action
     * @param startTime date and time
     */
    public void onDateTimeChanged(LocalDateTime startTime) {

        mStartTime = startTime.minusMinutes(startTime.getMinute());

        AddMeetingViewState currentViewState = mAddMeetingViewStateMutableLiveData.getValue();

        if (currentViewState != null) {
            mAddMeetingViewStateMutableLiveData.setValue(
                    new AddMeetingViewState(
                            currentViewState.getRoomsAvailable(),
                            mStartTime,
                            mParticipants, currentViewState.getMeetingNameError(),
                            mRoom, currentViewState.getRoomError(),
                            currentViewState.getParticipantError()
                    )
            );
        }
    }

    /**
     * Create meeting
     * @return return false if meeting is not created
     */
    public boolean createMeeting() {
        VerifiedInputs verifiedInputs = verifyUserInputs();

        if (verifiedInputs != null) {
            mMeetingApiService.addMeeting(new Meeting(mMeetingApiService.getLastId()+1,
                    verifiedInputs.mMetingName,
                    verifiedInputs.mStartDateTime,
                    verifiedInputs.room,
                    verifiedInputs.participants
            ));
            return true;
        }
        return false;
    }

    /**
     * Set Participants list
     * @param participants participants list
     */
    public void setParticipants(List<Participant> participants){
        mParticipants = participants;

        AddMeetingViewState currentViewState = mAddMeetingViewStateMutableLiveData.getValue();
        if (currentViewState != null) {
            mAddMeetingViewStateMutableLiveData.setValue(
                    new AddMeetingViewState(
                            currentViewState.getRoomsAvailable(),
                            currentViewState.getStartTime(),
                            mParticipants, currentViewState.getMeetingNameError(),
                            mRoom, currentViewState.getRoomError(),
                            currentViewState.getParticipantError()
                    )
            );
        }
    }


    /**
     * Verify userInput and return null if input has error(s) else a VerifiedInput
     * @return VerifiedInput
     */
    private VerifiedInputs verifyUserInputs() {
        boolean areUserInputOk = true;
        String meetingNameError;
        if (mMeetingName == null || mMeetingName.isEmpty()) {
            meetingNameError = mResources.getString(R.string.name_input_error);
            areUserInputOk = false;
        } else {
            meetingNameError = null;
        }

        String roomError;
        if (mRoom == null) {
            roomError = mResources.getString(R.string.room_input_error);
            areUserInputOk = false;
        } else {
            roomError = null;
        }
        String participantError;
        if(mParticipants.size() == 0){
            participantError = mResources.getString(R.string.participants_input_error);
            areUserInputOk = false;
        }
        else {
            participantError = null;
        }

        mAddMeetingViewStateMutableLiveData.setValue(
                new AddMeetingViewState(
                        mMeetingApiService.getAvailableRooms(mStartTime),
                        mStartTime,
                        mParticipants, meetingNameError,
                        mRoom, roomError, participantError
                )
        );

        if (areUserInputOk) {
            return new VerifiedInputs(
                    mMeetingName,
                    mStartTime,
                    mRoom,
                    mParticipants
            );
        }
        return null;
    }

    /**
     * Class VerifiedInput
     *
     */
    private static class VerifiedInputs {
        @NonNull
        private final String mMetingName;
        private final LocalDateTime mStartDateTime;
        @NonNull
        private final Room room;
        @NonNull
        private final List<Participant> participants;
        /**
         * VerifiedInput
         * @param mMetingName meeting name
         * @param startDateTime date and time
         * @param room room selected
         * @param participants list of participants
         */
        public VerifiedInputs(@NonNull String mMetingName, @NonNull LocalDateTime startDateTime, @NonNull Room room, @NonNull List<Participant> participants) {
            this.mMetingName = mMetingName;
            this.mStartDateTime = startDateTime;
            this.room = room;
            this.participants = participants;
        }
    }
    /**
     * Abstract Class AddMeetingViewAction
     */
    public abstract static class AddMeetingViewAction {
        /**
         * Static Class DisplayDateTimePicker
         */
        public static class DisplayDateTimePicker extends AddMeetingViewAction {
            /**
             * LocalDateTime
             */
            private final LocalDateTime mLocalDateTime;
            /**
             * @param localDateTime date and time
             */
            DisplayDateTimePicker(LocalDateTime localDateTime) {
                mLocalDateTime = localDateTime;
            }
            /**
             * return LocalDateTime
             */
            public LocalDateTime getLocalDateTime() {
                return mLocalDateTime;
            }
            /**
             * compare LocalDateTime of 2 DisplayDateTimePicker
             * @param o displayTimePicker object
             * @return boolean
             */
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                DisplayDateTimePicker that = (DisplayDateTimePicker) o;
                return mLocalDateTime == that.getLocalDateTime();
            }
            /**
             * @return hashcode
             */
            @Override
            public int hashCode() {
                return Objects.hash(mLocalDateTime);
            }
            /**
             * @return String
             */
            @NonNull
            @Override
            public String toString() {
                return "DisplayTimePicker{" + mLocalDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)) + '}';
            }
        }
    }
}
