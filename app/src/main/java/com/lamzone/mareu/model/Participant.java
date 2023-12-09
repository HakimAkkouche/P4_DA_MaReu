package com.lamzone.mareu.model;

/**
 * Participant class
 * @author Hakim
 */
public class Participant {
    private final String mEmailParticipant;

    /**
     * @param emailParticipant string participant
     */
    public Participant(String emailParticipant) {
        this.mEmailParticipant = emailParticipant;
    }
    /**
     * @return String email
     */
    public String getEmailParticipant() {
        return mEmailParticipant;
    }
}
