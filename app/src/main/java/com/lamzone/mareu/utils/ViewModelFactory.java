package com.lamzone.mareu.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lamzone.mareu.MainApplication;
import com.lamzone.mareu.ui.addmeeting.viewmodel.AddMeetingViewModel;
import com.lamzone.mareu.ui.listmeetings.viewmodel.MeetingsViewModel;

/**
 * ViewModel Factory
 * @author Hakim
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    /**
     * Factory
     */
    public static ViewModelFactory mFactory;
    /**
     * @return ViewModelFactory object
     */
    public static ViewModelFactory getInstance() {
        if(mFactory == null){
            synchronized (ViewModelFactory.class){
                if(mFactory == null){
                    mFactory = new ViewModelFactory();
                }
            }
        }
        return mFactory;
    }
    /**
     * return the correct ViewModel class
     * @param modelClass class model
     * @return ViewModel of modelClass
     * @param <T> object parameters
     */
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass){
        if(modelClass.isAssignableFrom(MeetingsViewModel.class)){
            return (T) new MeetingsViewModel(MainApplication.getInstance().getResources());
        } else if (modelClass.isAssignableFrom(AddMeetingViewModel.class)) {
            return (T) new AddMeetingViewModel(
                    MainApplication.getInstance().getResources());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
