package com.lamzone.mareu.ui_mareu.listmeetings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.lamzone.mareu.R;
import com.lamzone.mareu.databinding.FragmentMeetingsListBinding;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.ui_mareu.listmeetings.filter_room.OnRoomSelectedListener;
import com.lamzone.mareu.ui_mareu.listmeetings.filter_room.RoomFilterAdapter;
import com.lamzone.mareu.ui_mareu.listmeetings.viewmodel.MeetingsViewModel;
import com.lamzone.mareu.utils.ViewModelFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * MeetingsFragment Main fragment class
 */
public class MeetingsFragment extends Fragment implements
        OnMeetingsClickedListener, OnRoomSelectedListener {
    private FragmentMeetingsListBinding mBinding;
    private MeetingsViewModel mMeetingsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentMeetingsListBinding.inflate(inflater, container, false);
        mMeetingsViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingsViewModel.class);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                for (int i = 0; i < menu.size();  i++){
                    menu.getItem(i).setVisible(true);
                }
            }
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.action_filter_by_room){
                    changeVisibilityWithAnimation(mBinding.meetingRoomsRecyclerview);
                }
                else if(menuItem.getItemId() == R.id.action_filter_by_date){
                    onDateClickedListener();
                }
                return true;
            }
        });
        mBinding.fabAddMeeting.setOnClickListener(view -> NavHostFragment.findNavController(MeetingsFragment.this)
                .navigate(R.id.action_MeetingsFragment_to_AddFragment));

        return mBinding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Initialize Meetings RecyclerView
     */
    private void initRecyclerView(){
        final MeetingListAdapter adapter = new MeetingListAdapter(this);
        RecyclerView  meetingRecyclerView = mBinding.meetingListView;
        meetingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        meetingRecyclerView.setAdapter(adapter);

        final RoomFilterAdapter roomFilterAdapter = new RoomFilterAdapter(this);
        RecyclerView mRoomRecyclerView = mBinding.meetingRoomsRecyclerview;
        mRoomRecyclerView.setAdapter(roomFilterAdapter);

        mMeetingsViewModel.getViewStateLiveData().observe(this, viewState -> {
            adapter.submitList(viewState.getMeetingViewStateItems());
            roomFilterAdapter.submitList(viewState.getMeetingViewStateRoomFilterItems());
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    /**
     * Call onDeleteMeeting from the ViewModel
     * @param meetingId Identifier
     */
    @Override
    public void onDeleteMeetingClicked(int meetingId) {
        mMeetingsViewModel.onDeleteMeetingClicked(meetingId);
    }

    /**
     * call viewmodel to set the room filter
     * @param room room selected
     */
    @Override
    public void onRoomSelected(@NonNull Room room) {
        mMeetingsViewModel.onRoomSelected(room);
    }
    /**
     * call viewmodel to set the date filter
     */
    @Override
    public void onDateClickedListener() {
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now());


        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Choisir une date")
                .setNegativeButtonText("Annuler")
                .setPositiveButtonText("Valider")
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setCalendarConstraints(constraintsBuilderRange.build())
                .build();
        datePicker.show(getParentFragmentManager(), null);
        datePicker.addOnPositiveButtonClickListener(selection -> {
            LocalDate date = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate();
            mMeetingsViewModel.onDateSelected(date);
        });
        datePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetingsViewModel.onDateSelected(null);
            }
        });

    }

    /**
     * Handle Transition animation
     * @param view view
     */
    private void changeVisibilityWithAnimation(@NonNull View view) {
        boolean isViewActuallyVisible = view.getVisibility() == View.VISIBLE;

        TransitionManager.endTransitions(mBinding.getRoot());

        TransitionManager.beginDelayedTransition(mBinding.getRoot());

        if (isViewActuallyVisible) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}