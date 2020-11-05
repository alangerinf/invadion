package com.bitlicon.purolator.activity;

import androidx.fragment.app.FragmentActivity;

import com.bitlicon.purolator.AppController;

/**
 * Created by EduardoAndrés on 07/12/2015.
 */
public abstract class ControlFragmentActivity extends FragmentActivity
{
    private static final String TAG = ControlFragmentActivity.class.getName();

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
    }

}