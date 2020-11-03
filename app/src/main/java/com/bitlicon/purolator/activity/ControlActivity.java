package com.bitlicon.purolator.activity;

import android.app.Activity;
import android.util.Log;

import com.bitlicon.purolator.AppController;

/**
 * Created by EduardoAndrés on 07/12/2015.
 */
public class ControlActivity extends Activity
{
    private static final String TAG=ControlActivity.class.getName();

    /**
     * Gets reference to global Application
     * @return must always be type of ControlApplication! See AndroidManifest.xml
     */
    public AppController getApp()
    {
        return (AppController)this.getApplication();
    }

    @Override
    public void onUserInteraction()
    {
        super.onUserInteraction();
        getApp().touch();
        Log.d(TAG, "User interaction to " + this.toString());
    }

}