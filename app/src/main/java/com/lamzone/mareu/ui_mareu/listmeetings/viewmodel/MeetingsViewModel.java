package com.lamzone.mareu.ui_mareu.listmeetings.viewmodel;

import android.content.res.Resources;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lamzone.mareu.DI.DependencyInjector;
import com.lamzone.mareu.R;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.service.MeetingApiService;
import com.lamzone.mareu.service.MeetingRepository;
import com.lamzone.mareu.ui_mareu.listmeetings.filter_date.MeetingViewStateDateFilter;
import com.lamzone.mareu.ui_mareu.listmeetings.filter_room.MeetingViewStateRoomFilterItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * MeetingViewModel
 * @author Hakim
 */
public class MeetingsViewModel extends ViewModel {
    @NonNull
    private final Resources resources;
    @NonNull
    private final MeetingApiService mMeetingApiService = DependencyInjector.getInstance();
    private final MeetingRepository mMeetingRepository = new MeetingRepository();
    private final MediatorLiveData<MeetingViewState> meetingViewStateMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Map<Room, Boolean>> selectedRoomsLiveData = new MutableLiveData<>(populateMapWithAvailableRooms());
    private final MutableLiveData<LocalDate> selectedDateLiveData = new MediatorLiveData<>(null);
    private  LiveData<List<Meeting>> meetingsLiveData;

    /**
     * Constructor
     * @param resources ressources
     */
    public MeetingsViewModel(
            @NonNull final Resources resources
    ) {
        this.resources = resources;
        meetingsLiveData = mMeetingRepository.getMeetingsLiveData();
        addSourceMeetingModelsMediator();
    }

    /**
     * add source to meetingViewStateMediatorLiveData
     */
    private void addSourceMeetingModelsMediator() {

        meetingViewStateMediatorLiveData.addSource(meetingsLiveData, meetings ->
                meetingViewStateMediatorLiveData.setValue(
                        filterMeetings(
                                meetings,
                                selectedRoomsLiveData.getValue(),
                                selectedDateLiveData.getValue()
                        )));

        meetingViewStateMediatorLiveData.addSource(selectedRoomsLiveData, selectedRooms ->
                meetingViewStateMediatorLiveData.setValue(
                        filterMeetings(
                                meetingsLiveData.getValue(),
                                selectedRooms,
                                selectedDateLiveData.getValue()
                        )
                )
        );

        meetingViewStateMediatorLiveData.addSource(selectedDateLiveData, selectedDate ->
                meetingViewStateMediatorLiveData.setValue(
                        filterMeetings(
                                meetingsLiveData.getValue(),
                                selectedRoomsLiveData.getValue(),
                                selectedDate
                        )
                )
        );
    }

    /**
     * Filter Meetings
     * @param meetings list meetings
     * @param selectedRooms selected rooms
     * @param date date
     * @return list of filtered meetingViewState
     */
    @NonNull
    private MeetingViewState filterMeetings(
            @Nullable final List<Meeting> meetings,
            @Nullable final Map<Room, Boolean> selectedRooms,
            @NonNull final LocalDate date
    ) {
        if (selectedRooms == null) {
            throw new IllegalStateException("All internal LiveData must be initialized !");
        }

        List<Meeting> filteredMeetings = getFilteredMeetings(meetings, selectedRooms, date);

        List<MeetingViewStateItem> meetingViewStateItems = new ArrayList<>();
        for (Meeting filteredMeeting : filteredMeetings) {
            meetingViewStateItems.add(mapMeeting(filteredMeeting));
        }

        List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems = getMeetingViewStateRoomFilterItems(selectedRooms);
            MeetingViewStateDateFilter meetingViewStateDateFilter = new MeetingViewStateDateFilter(date);

        return new MeetingViewState(
                meetingViewStateItems,
                meetingViewStateRoomFilterItems,
                meetingViewStateDateFilter
        );
    }

    /**
     * return Filtered meetings
     * @param meetings meeting list
     * @param selectedRooms selected room
     * @param date date
     * @return list of filtered meetings
     */
    @NonNull
    private List<Meeting> getFilteredMeetings(@Nullable List<Meeting> meetings, @NonNull Map<Room, Boolean> selectedRooms, @Nullable LocalDate date) {
        List<Meeting> filteredMeetings = new ArrayList<>();

        if (meetings == null) {
            return filteredMeetings;
        }

        for (Meeting meeting : meetings) {

            boolean atLeastOneRoomIsSelected = false;
            boolean meetingRoomMatches = false;
            boolean meetingDateMatches = false;

            for (Map.Entry<Room, Boolean> roomEntry : selectedRooms.entrySet()) {
                Room room = roomEntry.getKey();
                boolean isRoomSelected = roomEntry.getValue();

                if (isRoomSelected) {
                    atLeastOneRoomIsSelected = true;
                }

                if (room == meeting.getRoom()) {
                    meetingRoomMatches = isRoomSelected;
                }
            }

            if (meeting.getStartTimeDate().toLocalDate().equals(date)) {
                meetingDateMatches = true;

            }

            if (!atLeastOneRoomIsSelected) {
                meetingRoomMatches = true;
            }

            if (date == null) {
                meetingDateMatches = true;
            }

            if (meetingRoomMatches && meetingDateMatches) {
                filteredMeetings.add(meeting);
            }
        }

        return filteredMeetings;
    }

    @NonNull
    private List<MeetingViewStateRoomFilterItem> getMeetingViewStateRoomFilterItems(@NonNull Map<Room, Boolean> selectedRooms) {
        List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems = new ArrayList<>();
        for (Map.Entry<Room, Boolean> entry : selectedRooms.entrySet()) {
            Room room = entry.getKey();
            Boolean isRoomSelected = entry.getValue();

            @ColorInt int textColorInt = ResourcesCompat.getColor(
                    resources,
                    isRoomSelected ? android.R.color.white : R.color.chipTextColor,
                    null
            );

            meetingViewStateRoomFilterItems.add(
                    new MeetingViewStateRoomFilterItem(
                            room,
                            textColorInt,
                            isRoomSelected
                    )
            );
        }
        return meetingViewStateRoomFilterItems;
    }
    /**
    * @return MeetingViewStateItem
    */
    @NonNull
    private MeetingViewStateItem mapMeeting(@NonNull Meeting meeting) {
        return new MeetingViewStateItem(
                meeting.getMeetingId(),
                meeting.getMeetingName(),
                meeting.getStartTimeDate(),
                meeting.getRoom(),
                meeting.getParticipants()
        );
    }
    @NonNull
    public LiveData<MeetingViewState> getViewStateLiveData() {
        return meetingViewStateMediatorLiveData;
    }

    /**
     *
     * @param meetingId identifier
     */
    public void onDeleteMeetingClicked(int meetingId) {

        mMeetingRepository.deleteMeeting(meetingId);
        meetingsLiveData = mMeetingRepository.getMeetingsLiveData();
    }
    @NonNull
    private Map<Room, Boolean> populateMapWithAvailableRooms() {
        Map<Room, Boolean> rooms = new LinkedHashMap<>();

        List<Room> roomList = mMeetingApiService.getRooms();
        for (Room room : roomList) {
            rooms.put(room, false);
        }

        return rooms;
    }
    // region Filter: Room
    public void onRoomSelected(@NonNull Room room) {
        Map<Room, Boolean> selectedRooms = selectedRoomsLiveData.getValue();

        if (selectedRooms == null) {
            return;
        }

        for (Map.Entry<Room, Boolean> entry : selectedRooms.entrySet()) {
            if (entry.getKey() == room) {
                entry.setValue(!entry.getValue());
                break;
            }
        }

        selectedRoomsLiveData.setValue(selectedRooms);
    }

    /**
     *
     * @param date date
     */
    public void onDateSelected(LocalDate date){
        selectedDateLiveData.setValue(date);
    }
}
