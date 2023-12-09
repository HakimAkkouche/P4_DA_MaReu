package com.lamzone.mareu.ui_mareu.listmeetings.filter_date;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class MeetingViewStateDateFilter
 * @author Hakim
 */
public class MeetingViewStateDateFilter {

    @NonNull
    private final LocalDate date;

    /**
     * Constructor
     * @param date date filter
     */
    public MeetingViewStateDateFilter(@NonNull LocalDate date) {
        this.date = date;
    }

    /**
     * @return date selected
     */
    @NonNull
    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewStateDateFilter that = (MeetingViewStateDateFilter) o;
        return date == that.date ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingViewStateRoomFilterItem{" +
                "date=" + date +
                '}';
    }
}
