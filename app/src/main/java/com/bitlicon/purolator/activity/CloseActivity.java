package com.bitlicon.purolator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.fragment.ClienteFragment;
import com.bitlicon.purolator.util.Constantes;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by EduardoAndrés on 07/12/2015.
 */
public class CloseActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.finish();
        this.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}