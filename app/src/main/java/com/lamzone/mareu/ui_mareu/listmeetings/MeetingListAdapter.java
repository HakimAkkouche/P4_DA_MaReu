package com.lamzone.mareu.ui_mareu.listmeetings;

import static com.lamzone.mareu.utils.TransitionUtils.getMeetingDetailsTransitionName;
import static com.lamzone.mareu.utils.TransitionUtils.getMeetingRoomTransitionName;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.databinding.FragmentMeetingBinding;
import com.lamzone.mareu.ui_mareu.listmeetings.viewmodel.MeetingViewStateItem;

/**
 * MeetingList Adapter
 * @author Hakim
 */
public class MeetingListAdapter extends ListAdapter<MeetingViewStateItem, MeetingListAdapter.MeetingViewHolder> {
    @NonNull
    private final OnMeetingsClickedListener mListener;
    public MeetingListAdapter(@NonNull OnMeetingsClickedListener listener) {
        super(new ListMeetingItemCallback());
        this.mListener = listener;
    }
    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentMeetingBinding binding = FragmentMeetingBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MeetingViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        holder.bind(getItem(position), mListener);
    }
    public class MeetingViewHolder extends RecyclerView.ViewHolder {
        FragmentMeetingBinding binding;
        private final ImageView img;
        private final TextView meetingDetails;
        private final TextView meetingParticipants;
        private final ImageButton removeImageButton;
        public MeetingViewHolder(FragmentMeetingBinding binding){
            super(binding.getRoot());
            this.binding = binding;

            this.img = binding.itemMeetingListIcon;
            this.meetingDetails = binding.itemMeetingNameList;
            this.meetingParticipants = binding.meetingParticipantsList;
            this.removeImageButton = binding.itemListDeleteButton;
        }
        public void bind(@NonNull MeetingViewStateItem meetingViewStateItem,
                         @NonNull OnMeetingsClickedListener listener){
            img.setImageResource(img.getResources().getIdentifier(meetingViewStateItem.getRoom().getIconRes(), "drawable", img.getContext().getPackageName()));

            meetingDetails.setText(meetingViewStateItem.toString());
            meetingDetails.setContentDescription(meetingViewStateItem.toString());

            meetingParticipants.setText(meetingViewStateItem.getParticipantsToString());
            meetingParticipants.setContentDescription(meetingViewStateItem.getParticipantsToString());

            removeImageButton.setOnClickListener(view -> {
                listener.onDeleteMeetingClicked(meetingViewStateItem.getMeetingId());
                notifyDataSetChanged();
            });

            img.setTransitionName(getMeetingRoomTransitionName(meetingViewStateItem.getMeetingId()));
            meetingDetails.setTransitionName(getMeetingDetailsTransitionName(meetingViewStateItem.getMeetingId()));
        }
    }
    private static class ListMeetingItemCallback extends DiffUtil.ItemCallback<MeetingViewStateItem> {

        @Override
        public boolean areItemsTheSame(@NonNull MeetingViewStateItem oldItem, @NonNull MeetingViewStateItem newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MeetingViewStateItem oldItem, @NonNull MeetingViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}
