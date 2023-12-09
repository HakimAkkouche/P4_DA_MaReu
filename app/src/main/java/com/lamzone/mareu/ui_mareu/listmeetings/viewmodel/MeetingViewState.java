package com.lamzone.mareu.ui_mareu.listmeetings.viewmodel;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

import com.lamzone.mareu.ui_mareu.listmeetings.filter_date.MeetingViewStateDateFilter;
import com.lamzone.mareu.ui_mareu.listmeetings.filter_room.MeetingViewStateRoomFilterItem;

/**
 * MeetingViewState
 * @author Hakim
 */
public class MeetingViewState {
    @NonNull
    private final List<MeetingViewStateItem> mMeetingViewStateItems;
    @NonNull
    private final List<MeetingViewStateRoomFilterItem> mMeetingViewStateRoomFilterItems;
    @NonNull
    private final MeetingViewStateDateFilter mMeetingViewStateDateFilter;

    /**
     * Constructor
     * @param meetingViewStateItems meetingViewStateItems
     * @param meetingViewStateRoomFilterItem meetingViewStateRoomFilterItem
     * @param meetingViewStateDateFilter meetingViewStateDateFilter
     */
    public MeetingViewState(
            @NonNull List<MeetingViewStateItem> meetingViewStateItems,
            @NonNull List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItem,
            @NonNull MeetingViewStateDateFilter meetingViewStateDateFilter) {
        this.mMeetingViewStateItems = meetingViewStateItems;
        this.mMeetingViewStateRoomFilterItems = meetingViewStateRoomFilterItem;
        this.mMeetingViewStateDateFilter = meetingViewStateDateFilter;
    }

    @NonNull
    public List<MeetingViewStateItem> getMeetingViewStateItems() {
        return mMeetingViewStateItems;
    }

    @NonNull
    public List<MeetingViewStateRoomFilterItem> getMeetingViewStateRoomFilterItems() {
        return mMeetingViewStateRoomFilterItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewState that = (MeetingViewState) o;
        return mMeetingViewStateItems.equals(that.mMeetingViewStateItems)
                && mMeetingViewStateRoomFilterItems.equals(that.mMeetingViewStateRoomFilterItems)
                && mMeetingViewStateDateFilter.equals(that.mMeetingViewStateDateFilter);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(mMeetingViewStateItems, mMeetingViewStateRoomFilterItems, mMeetingViewStateDateFilter);
    }
    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public String toString() {
        return "MeetingViewState{" +
                "mMeetingViewStateItems=" + mMeetingViewStateItems +
                "mMeetingViewStateRoomFilterItems=" + mMeetingViewStateRoomFilterItems +
                "mMeetingViewStateDateFilter=" + mMeetingViewStateDateFilter +
                '}';
    }
}
