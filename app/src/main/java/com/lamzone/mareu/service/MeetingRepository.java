package com.lamzone.mareu.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lamzone.mareu.DI.DependencyInjector;
import com.lamzone.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingRepository {

    private final MeetingApiService meetingApiService = DependencyInjector.getInstance();
    private final MutableLiveData<List<Meeting>> meetingsMutableLiveData;

    public MeetingRepository() {
        List<Meeting> mMeetings = meetingApiService.getMeetings();
        meetingsMutableLiveData = new MutableLiveData<>(new ArrayList<>());
        meetingsMutableLiveData.setValue(mMeetings);
    }

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsMutableLiveData;
    }
    public void deleteMeeting(int meetingId) {
        meetingApiService.deleteMeeting(meetingId);
        meetingsMutableLiveData.setValue(meetingApiService.getMeetings());
    }
}
