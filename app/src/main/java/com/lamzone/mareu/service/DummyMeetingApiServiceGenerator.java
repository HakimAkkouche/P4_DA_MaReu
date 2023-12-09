package com.lamzone.mareu.service;

import androidx.annotation.NonNull;

import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Participant;
import com.lamzone.mareu.model.Room;

import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public abstract class DummyMeetingApiServiceGenerator {

    private final static List<Participant> DUMMY_PARTICIPANT_LIST = Arrays.asList(
            new Participant("j.jenkins@lamzone.fr"),
            new Participant("o.mayert@lamzone.fr"),
            new Participant("b.jacobs@lamzone.fr"),
            new Participant("a.hudson@lamzone.fr"),
            new Participant("s.farrell@lamzone.fr"),
            new Participant("r.turner@lamzone.fr"),
            new Participant("e.casper@lamzone.fr"),
            new Participant("m.funk@lamzone.fr"),
            new Participant("k.torp@lamzone.fr"),
            new Participant("m.rodriguez@lamzone.fr"),
            new Participant("o.schmitt@lamzone.fr"),
            new Participant("j.russel@lamzone.fr"),
            new Participant("a.mimoun@lamzone.fr"),
            new Participant("t.parker@lamzone.fr"),
            new Participant("a.mohammed@lamzone.fr"),
            new Participant("z.zidane@lamzone.fr"),
            new Participant("t.riner@lamzone.fr"),
            new Participant("s.loeb@lamzone.fr"),
            new Participant("s.bergnau@lamzone.fr")
    );
    private final static List<Room> DUMMY_ROOM_LIST = Arrays.asList(
            new Room(1,"Peach"),
            new Room(2,"Mario"),
            new Room(3,"Luigi"),
            new Room(4,"Toad"),
            new Room(5,"Daisy"),
            new Room(6,"Wario"),
            new Room(7,"Waluigi"),
            new Room(8,"Yoshi"),
            new Room(9,"Bowser")
    );
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting( 0, "Réunion A",
                    LocalDateTime.of(2023, LocalDateTime.now().getMonthValue(), 13,8,0),
                    DUMMY_ROOM_LIST.get(0),
                    generateParticipantsList()),
            new Meeting(1, "Réunion B",
                    LocalDateTime.of(2023,LocalDateTime.now().getMonthValue(),13,14,0),

                    DUMMY_ROOM_LIST.get(1),
                    generateParticipantsList()),
            new Meeting(2, "Réunion C",
                    LocalDateTime.of(2023,LocalDateTime.now().getMonthValue(),14,8,30),
                    DUMMY_ROOM_LIST.get(5),
                    generateParticipantsList()),
            new Meeting(3, "Réunion D",
                    LocalDateTime.of(2023,LocalDateTime.now().getMonthValue(),14,8,0),
                    DUMMY_ROOM_LIST.get(3),
                    generateParticipantsList()),
            new Meeting(4, "Réunion E",
                    LocalDateTime.of(2023,LocalDateTime.now().getMonthValue(),16,8,0),
                    DUMMY_ROOM_LIST.get(4),
                    generateParticipantsList()),
            new Meeting( 5, "Réunion F",
                    LocalDateTime.of(2023,LocalDateTime.now().getMonthValue(),16,8,0),
                    DUMMY_ROOM_LIST.get(5),
                    generateParticipantsList()),
            new Meeting(6, "Réunion G",
                    LocalDateTime.of(2023,LocalDateTime.now().getMonthValue(),17,10,0),
                    DUMMY_ROOM_LIST.get(6),
                    generateParticipantsList())
    );

    /**
     * @return a dummy meeting list
     */
    @NonNull
    @Contract(" -> new")
    static List<Meeting> generateMeetings(){
        return new ArrayList<>(DUMMY_MEETINGS);
    }
    /**
     * @return List of participants
     */
    @NonNull
    public static List<Participant> generateParticipantsList()
    {
        List<Participant> participantsList = new ArrayList<>();
        for( int i = 0; i < 10; i++)
        {
            participantsList.add(
                    DUMMY_PARTICIPANT_LIST.get(new Random().nextInt(DUMMY_PARTICIPANT_LIST.size())));
        }
        return participantsList;
    }

    /**
     * @return a dummy room list
     */
    public static List<Room> getDummyRoomList()
    {
        return DUMMY_ROOM_LIST;
    }

}
