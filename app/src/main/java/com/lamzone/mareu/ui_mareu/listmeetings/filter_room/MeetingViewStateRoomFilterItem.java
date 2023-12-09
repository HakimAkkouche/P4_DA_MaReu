package com.lamzone.mareu.ui_mareu.listmeetings.filter_room;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.lamzone.mareu.model.Room;

import java.util.Objects;

/**
 * Class MeetingViewStateRoomFilter
 * @author Hakim
 */
public class MeetingViewStateRoomFilterItem {

    @NonNull
    private final Room room;
    @ColorInt
    private final int textColorInt;
    private final boolean isSelected;

    /**
     * Constructor
     * @param room room
     * @param textColorInt color selected room
     * @param isSelected boolean selected
     */
    public MeetingViewStateRoomFilterItem(@NonNull Room room, @ColorInt int textColorInt, boolean isSelected) {
        this.room = room;
        this.textColorInt = textColorInt;
        this.isSelected = isSelected;
    }

    /**
     * @return the room
     */
    @NonNull
    public Room getRoom() {
        return room;
    }
    /**
     * @return the color of the room
     */
    @ColorInt
    public int getTextColorInt() {
        return textColorInt;
    }

    /**
     * @return if the room is selected
     */
    public boolean isSelected() {
        return isSelected;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewStateRoomFilterItem that = (MeetingViewStateRoomFilterItem) o;
        return textColorInt == that.textColorInt &&
            isSelected == that.isSelected &&
            room == that.room;
    }
    @Override
    public int hashCode() {
        return Objects.hash(room, textColorInt, isSelected);
    }
    @NonNull
    @Override
    public String toString() {
        return "MeetingViewStateRoomFilterItem{" +
            "room=" + room +
            ", textColorInt=" + textColorInt +
            ", isSelected=" + isSelected +
            '}';
    }
}
