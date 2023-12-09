package com.lamzone.mareu.ui_mareu.addmeeting;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.databinding.CardAddParticipantLayoutBinding;
import com.lamzone.mareu.databinding.CardParticipantLayoutBinding;
import com.lamzone.mareu.model.Participant;

import java.util.List;

/**
 * Par
 * @author Hakim
 */
public class ParticipantListAdapter extends ListAdapter<Participant, ParticipantListAdapter.ParticipantViewHolder> {
    private final int HEADER = 0;
    private final int NORMAL_ITEM = 1;
    private final List<Participant> mParticipants;

    /**
     * Constructor
     * @param participants list of participants
     */
    public ParticipantListAdapter(@NonNull List<Participant> participants) {
        super(new ListParticipantItemCallback());
        mParticipants = participants;
    }

    /**
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return ParticipantViewHolder
     */
    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == HEADER) {
            CardAddParticipantLayoutBinding cardAddParticipantLayoutBinding = CardAddParticipantLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
            return new ParticipantViewHolder(cardAddParticipantLayoutBinding);
        }
        else {
            CardParticipantLayoutBinding cardParticipantLayoutBinding = CardParticipantLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
            return new ParticipantViewHolder(cardParticipantLayoutBinding);
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ParticipantViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ParticipantViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        holder.bind(mParticipants.get(position), getItemViewType(position));
    }

    /**
     * return HEADER for the first line to put the input layout on the first line of the recyclerview
     * @param position position to query
     * @return int position
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return NORMAL_ITEM;
        }
    }
    @Override
    public int getItemCount() {
        return mParticipants == null ? 0 : mParticipants.size();
    }

    public class ParticipantViewHolder extends RecyclerView.ViewHolder {
        private EditText emailEditText;
        private ImageButton addEmailImageButton;
        private TextView emailTextView;
        private ImageButton removeButton;

        public ParticipantViewHolder(@NonNull CardParticipantLayoutBinding itemView) {
            super(itemView.getRoot());
            emailTextView = itemView.itemListEmail;
            removeButton = itemView.itemListDeleteButton;
        }
        public ParticipantViewHolder(@NonNull CardAddParticipantLayoutBinding itemView) {
            super(itemView.getRoot());
            emailEditText = itemView.addEmailEdittext;
            addEmailImageButton =itemView.addEmailButton;

        }
        public void bind(Participant participant, int position){
            if(position == HEADER){
                emailEditText.setText(participant.getEmailParticipant());
                addEmailImageButton.setOnClickListener(v -> {
                    if(!emailEditText.getText().toString().isEmpty()
                            && Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()){
                        mParticipants.add(new Participant(emailEditText.getText().toString()));
                        emailEditText.setText("");
                        notifyDataSetChanged();
                    }
                    else {
                        emailEditText.setError("Incorrect email");
                    }
                });
            }
            else {
                emailTextView.setText(participant.getEmailParticipant());
                removeButton.setOnClickListener(v -> {
                    mParticipants.remove(participant);
                    notifyDataSetChanged();
                });
            }
        }
    }

    private static class ListParticipantItemCallback extends DiffUtil.ItemCallback<Participant> {

        @Override
        public boolean areItemsTheSame(@NonNull Participant oldItem, @NonNull Participant newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Participant oldItem, @NonNull Participant newItem) {
            return oldItem.getEmailParticipant().equals(newItem.getEmailParticipant());
        }
    }
}

