package com.lamzone.mareu.ui.addmeeting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.lamzone.mareu.di.DependencyInjector;
import com.lamzone.mareu.databinding.FragmentAddMeetingBinding;
import com.lamzone.mareu.model.Participant;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.service.MeetingApiService;
import com.lamzone.mareu.ui.addmeeting.viewmodel.AddMeetingViewModel;
import com.lamzone.mareu.utils.ViewModelFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Class AddMeetingFragment
 * @author Hakim
 */
public class AddMeetingFragment extends Fragment {
    private final MeetingApiService mMeetingApiService = DependencyInjector.getInstance();
    private FragmentAddMeetingBinding mBinding;
    private LocalDateTime mDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toLocalDate().atStartOfDay();
    private AutoCompleteTextView mSelectRoomAutocompleteTv;
    RoomSpinnerAdapter mRoomAdapter;
    private final List<Participant> mParticipants = new ArrayList<>();
    private LocalDateTime mStartTimeDate;
    AddMeetingViewModel mViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAddMeetingBinding.inflate(inflater, container, false);

        mBinding.selectRoomAutocompleteTv.setClickable(false);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                for (int i = 0; i < menu.size();  i++){
                    menu.getItem(i).setVisible(false);
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });

        return mBinding.getRoot();
        //return mBinding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);

        initNameEditText(mViewModel, mBinding.addMeetingName);
        initDatePicker();
        initRoomDropDownList();
        initParticipantsListAdapter();


        mViewModel.getViewStateLiveData().observe(getViewLifecycleOwner(), viewState -> {
            mSelectRoomAutocompleteTv.setError(viewState.getRoomError());
        });

        mViewModel.getViewActionLiveData().observe(getViewLifecycleOwner(), addMeetingViewAction -> {
            if (addMeetingViewAction instanceof AddMeetingViewModel.AddMeetingViewAction.DisplayDateTimePicker) {
                initDatePicker();
            }
        });
        mBinding.addSaveButton.setOnClickListener(v -> {
            if(mParticipants.size() == 1){
                Toast.makeText(getContext(), "Please add guest", Toast.LENGTH_SHORT).show();
            }
            else {

                mParticipants.remove(0);
                mViewModel.setParticipants(mParticipants);
                if(mViewModel.createMeeting()){
                    Navigation.findNavController(v).navigate(com.lamzone.mareu.R.id.action_AddFragment_to_MeetingsFragment);
                }
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
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
     * Initialize Meeting name edittext
     * @param viewModel viewModel
     * @param meetingEditText meetingEditText
     */
    private void initNameEditText(@NonNull AddMeetingViewModel viewModel, @NonNull EditText meetingEditText) {
        meetingEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onNameChanged(s.toString());
            }
        });
    }

    /**
     * initialize date picker
     */
    private void initDatePicker(){
        mBinding.addDateTextview.setText(mDate.format(DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm")));
        mBinding.addDateTextview.setFocusable(false);
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now());


        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Choisir une date")
                .setNegativeButtonText("Annuler")
                .setPositiveButtonText("Valider")
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setCalendarConstraints(constraintsBuilderRange.build())
                .build();
        mBinding.addDateTextview.setOnClickListener(v -> {
            mViewModel.onDateTimeClicked();
            datePicker.show(getParentFragmentManager(), null);
        });
        datePicker.addOnPositiveButtonClickListener(selection -> {
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTitleText("Choisir une heure")
                    .setHour(LocalDateTime.now().getHour())
                    .setMinute(0)
                    .setNegativeButtonText("Annuler")
                    .setPositiveButtonText("Valider")
                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .build();

            mDate = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();

            timePicker.show(getActivity().getSupportFragmentManager(), "TimePicker");

            timePicker.addOnPositiveButtonClickListener(v -> {
                mDate = LocalDateTime.of(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), timePicker.getHour(), 0);
                mViewModel.onDateTimeChanged(mDate);
                mBinding.addDateTextview.setText(mDate.format(DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm")));

                mStartTimeDate = mDate;
                mRoomAdapter = new RoomSpinnerAdapter(requireContext(), mMeetingApiService.getAvailableRooms(mStartTimeDate));
                mRoomAdapter.notifyDataSetChanged();
                mSelectRoomAutocompleteTv.setAdapter(mRoomAdapter);

                mBinding.roomEmailLayout.setFocusable(true);
            });
        });
    }
    /**
     * initialize drop down room list
     */
    private void initRoomDropDownList(){

        //Spinner selectRoomAutocompleteTv;
        mSelectRoomAutocompleteTv = mBinding.selectRoomAutocompleteTv;

        List<Room> roomList = mMeetingApiService.getAvailableRooms(mDate);
        mRoomAdapter = new RoomSpinnerAdapter(requireContext(), roomList);
        mSelectRoomAutocompleteTv.setAdapter(mRoomAdapter);
        mSelectRoomAutocompleteTv.setOnItemClickListener((parent, view, position, id) ->
                mViewModel.onRoomChanged(mRoomAdapter.getItem(position))
        );
    }
    /**
     * initialize participant recyclerview
     */
    private void initParticipantsListAdapter() {

        RecyclerView mEmailsRecyclerView = mBinding.emailRecyclerview;
        mParticipants.add(new Participant(""));
        mEmailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ParticipantListAdapter mParticipantListAdapter = new ParticipantListAdapter(mParticipants);

        mEmailsRecyclerView.setAdapter(mParticipantListAdapter);
    }
}