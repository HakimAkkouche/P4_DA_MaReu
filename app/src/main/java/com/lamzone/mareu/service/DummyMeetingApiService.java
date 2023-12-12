package com.lamzone.mareu.service;

import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {
    private static volatile DummyMeetingApiService INSTANCE = null;
    private final List<Meeting> mMeetings;
    /**
     * {@inheritDoc}
     */
    public static DummyMeetingApiService getInstance() {
        if(INSTANCE == null){
            synchronized (DummyMeetingApiService.class)  {
                if(INSTANCE == null){
                    INSTANCE = new DummyMeetingApiService();
                }
            }
        }
        return INSTANCE;
    }
    /**
     * set dummy  meetings from dummy api generator
     */
    private DummyMeetingApiService(){
        mMeetings = DummyMeetingApiServiceGenerator.generateMeetings();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(int meetingId) {
        Meeting meeting = mMeetings.stream()
                .filter(meet -> meetingId == meet.getMeetingId())
                .findAny()
                .orElse(null);
        mMeetings.remove(meeting);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Room> getAvailableRooms(LocalDateTime startTime) {
        List<Room> availableRooms = new ArrayList<>(DummyMeetingApiServiceGenerator.getDummyRoomList());
        for (Meeting meet : mMeetings) {
            if( meet.getStartTimeDate().equals(startTime) ) {
                if (availableRooms.contains(meet.getRoom())) {
                    availableRooms.remove(meet.getRoom());
                }
            }
        }
        return availableRooms;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Room> getRooms() {
        return DummyMeetingApiServiceGenerator.getDummyRoomList();

    }
    /**
     * {@inheritDoc}
     */
    public int getLastId(){
        return mMeetings.get(mMeetings.size()-1).getMeetingId();
    }
}
