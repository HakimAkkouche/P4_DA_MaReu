package com.lamzone.mareu.ui_mareu.listmeetings.filter_room;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.lamzone.mareu.databinding.MeetingRoomItemviewBinding;

/**
 * RoomFilterAdapter
 * @author Hakim
 */
public class RoomFilterAdapter extends ListAdapter<MeetingViewStateRoomFilterItem, RoomFilterAdapter.ViewHolder> {
    @NonNull
    private final OnRoomSelectedListener listener;
    /**
     * Constructor
     * @param listener listener
     */
    public RoomFilterAdapter(@NonNull OnRoomSelectedListener listener) {
        super(new RoomFilterAdapterDiffCallback());

        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MeetingRoomItemviewBinding meetingRoomItemviewBinding = MeetingRoomItemviewBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(meetingRoomItemviewBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @VisibleForTesting
        public final Chip chip;

        public ViewHolder(@NonNull MeetingRoomItemviewBinding meetingRoomItemviewBinding) {
            super(meetingRoomItemviewBinding.getRoot());

            chip = meetingRoomItemviewBinding.meetingRoomItemChip;
        }
        public void bind(
            @NonNull final MeetingViewStateRoomFilterItem roomItemModel,
            @NonNull final OnRoomSelectedListener listener
        ) {

            chip.setChipIcon(ContextCompat.getDrawable(chip.getContext(),
                    chip.getResources()
                            .getIdentifier(roomItemModel.getRoom()
                                    .getIconRes(),
                                    "drawable",
                                    chip.getContext().getPackageName())));
            chip.setText(roomItemModel.getRoom().getRoomName());
            chip.setTextColor(roomItemModel.getTextColorInt());
            chip.setRippleColorResource(chip.getResources().getIdentifier(roomItemModel.getRoom().getColorRes(), "color", chip.getContext().getPackageName()));

            int[][] states = new int[][]{
                new int[]{android.R.attr.state_selected},
                new int[]{-android.R.attr.state_selected}
            };

            int[] colors = new int[]{
                ContextCompat.getColor(chip.getContext(),
                                chip.getResources().getIdentifier(roomItemModel.getRoom().getColorRes(),
                                        "color",
                                        chip.getContext().getPackageName())),
                0
            };

            ColorStateList colorStateList = new ColorStateList(states, colors);

            chip.setChipBackgroundColor(colorStateList);
            chip.setContentDescription(chip.getResources().getString(chip.getResources().getIdentifier("room", "string", chip.getContext().getPackageName())) +
                    " "+
                    roomItemModel.getRoom().getRoomName());

            chip.setOnCheckedChangeListener(null);
            chip.setChecked(roomItemModel.isSelected());
            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> listener.onRoomSelected(roomItemModel.getRoom()));
        }
    }

    private static class RoomFilterAdapterDiffCallback extends DiffUtil.ItemCallback<MeetingViewStateRoomFilterItem> {
        @Override
        public boolean areItemsTheSame(@NonNull MeetingViewStateRoomFilterItem oldItem, @NonNull MeetingViewStateRoomFilterItem newItem) {
            return oldItem.getRoom().equals(newItem.getRoom());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MeetingViewStateRoomFilterItem oldItem, @NonNull MeetingViewStateRoomFilterItem newItem) {
            return oldItem.isSelected() == newItem.isSelected();
        }
    }
}
