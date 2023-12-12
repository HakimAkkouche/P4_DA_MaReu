package com.lamzone.mareu.ui.addmeeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lamzone.mareu.databinding.RoomSpinnerItemBinding;
import com.lamzone.mareu.model.Room;

import java.util.List;

/**
 * Class RoomSpinnerAdapter
 * @author Hakim
 */
class RoomSpinnerAdapter extends ArrayAdapter<Room> {
    /**
     * Constructor
     * @param context context
     * @param rooms room list
     */
    public RoomSpinnerAdapter(@NonNull Context context, List<Room> rooms) {
        super(context, com.lamzone.mareu.R.layout.room_spinner_item, rooms);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @NonNull
    public View getCustomView(int position, @NonNull ViewGroup parent) {
        RoomSpinnerItemBinding binding = RoomSpinnerItemBinding.inflate(LayoutInflater.from(parent.getContext()));

        ImageView icon = binding.roomItemIvIcon;
        TextView label = binding.roomItemTvName;

        Room room = getItem(position);

        assert room != null;

        icon.setBackgroundResource(icon.getResources().getIdentifier(room.getIconRes(), "drawable", icon.getContext().getPackageName()));
        label.setText(room.getRoomName());
        label.setContentDescription("Salle "+ room.getRoomName());

        return binding.getRoot();
    }
}
