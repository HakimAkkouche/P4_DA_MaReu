package com.lamzone.mareu.di;

import com.lamzone.mareu.BuildConfig;
import com.lamzone.mareu.service.DummyMeetingApiService;
import com.lamzone.mareu.service.MeetingApiService;

/**
 * Dependency injector
 * @author Hakim
 */
public class DependencyInjector {

    private static MeetingApiService mService;

    /**
     *
     * @return MeetingApiService singleton either Dummy in debugmode or real api in release mode
     */
    public static MeetingApiService getInstance(){
        if(BuildConfig.DEBUG){
            mService = DummyMeetingApiService.getInstance();
        }
        else {
            //TODO sets real Api
        }
        return mService;
    }
}

