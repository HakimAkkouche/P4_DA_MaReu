package com.lamzone.mareu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.lamzone.mareu.DI.DependencyInjector;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Participant;
import com.lamzone.mareu.service.DummyMeetingApiServiceGenerator;
import com.lamzone.mareu.service.MeetingApiService;


import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingUnitTest {
    private MeetingApiService service;

    @Before
    public void setup() {
        service = DependencyInjector.getInstance();
    }

    @Test
    public void getMeetingsWithSuccess(){
        List<Meeting> meetingListActual = service.getMeetings();
        List<Meeting> meetingListExpected = DummyMeetingApiServiceGenerator.DUMMY_MEETINGS;
        assertTrue(meetingListActual.containsAll(meetingListExpected));
    }
    @Test
    public void getRoomsWithSuccess(){
        int nbRooms = service.getRooms().size();
        assertEquals(nbRooms, 9);
    }
    @Test
    public void addMeetingWithSuccess(){
        List<Participant> participants = DummyMeetingApiServiceGenerator.generateParticipantsList();
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(LocalDateTime.now().getMinute());
        service.addMeeting(new Meeting(111111, "test date",
                dateTime,
                service.getRooms().get(0),
                participants
        ));
        Meeting meeting = service.getMeetings().get(service.getMeetings().size()-1);
        assertEquals(meeting.getMeetingId(), 111111);
        assertEquals(meeting.getMeetingName(), "test date");
        assertEquals(meeting.getStartTimeDate(), dateTime);
        assertEquals(meeting.getRoom(), service.getRooms().get(0));
        assertEquals(meeting.getParticipants(), participants);
    }
    @Test
    public void getAvailableRoomsWithSuccess(){
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(LocalDateTime.now().getMinute());
        int nbRooms = service.getRooms().size();
        service.addMeeting(new Meeting(111111, "test date",
                dateTime,
                service.getRooms().get(0),
            DummyMeetingApiServiceGenerator.generateParticipantsList()
                ));

        assertEquals(service.getAvailableRooms(dateTime).size(), nbRooms - 1);
    }

    @Test
    public void removeMeetingWithSuccess(){
        Meeting meeting = service.getMeetings().get(3);
        service.deleteMeeting(meeting.getMeetingId());
        assertFalse(service.getMeetings().contains(meeting));
    }

}
