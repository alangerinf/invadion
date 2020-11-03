package com.bitlicon.purolator.activity;

import androidx.fragment.app.FragmentActivity;

import com.bitlicon.purolator.ControlApplication;

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
    public ControlApplication getApp()
    {
        return (ControlApplication)this.getApplication();
    }

    @Override
    public void onUserInteraction()
    {
        super.onUserInteraction();
        getApp().touch();
    }

}