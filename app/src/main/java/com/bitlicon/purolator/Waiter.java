package com.bitlicon.purolator;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bitlicon.purolator.activity.CloseActivity;
import com.bitlicon.purolator.activity.ResumenActivity;
import com.bitlicon.purolator.lib.SessionManager;

/**
 * Created by EduardoAndrés on 07/12/2015.
 */
public class Waiter extends Thread
{
    private static final String TAG=Waiter.class.getName();
    private long lastUsed;
    private long period;
    private boolean stop;
    private Application app;

    public Waiter(long period, Application app)
    {
        this.period=period;
        this.app = app;
        stop=false;
    }

    public void run()
    {
        long idle=0;
        this.touch();
        do
        {
            idle=System.currentTimeMillis()-lastUsed;
            Log.d(TAG, "Application is idle for " + idle + " ms");
            try
            {
                Thread.sleep(5000); //check every 5 seconds
            }
            catch (InterruptedException e)
            {
                Log.d(TAG, "Waiter interrupted!");
            }
            if(idle >= period)
            {
                idle=0;
                SessionManager sessionManager = new SessionManager(app.getApplicationContext());
                sessionManager.logoutUser();
                Thread.interrupted();

                Intent intent = new Intent(app.getApplicationContext(), CloseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                app.startActivity(intent);
            }
        }
        while(!stop);
        Log.d(TAG, "Finishing Waiter thread");
    }

    public synchronized void touch()
    {
        lastUsed=System.currentTimeMillis();
    }

    public synchronized void forceInterrupt()
    {
        this.interrupt();
    }

    public synchronized void setPeriod(long period)
    {
        this.period=period;
    }

}