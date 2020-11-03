package com.bitlicon.purolator;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bitlicon.purolator.dao.ConfiguracionDAO;
import com.bitlicon.purolator.entities.Configuracion;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by EduardoAndrés on 07/12/2015.
 */
public class ControlApplication extends Application
{
    private static final String TAG=ControlApplication.class.getName();
    private Waiter waiter;  //Thread which controls idle time

    // only lazy initializations here!
    @Override
    public void onCreate()
    {
        ConfiguracionDAO ConfigDao = new ConfiguracionDAO(getApplicationContext());
        Configuracion Config = ConfigDao.Obtener();

        initImageLoader(this.getApplicationContext());

        super.onCreate();
        Log.d(TAG, "Starting application" + this.toString());
        waiter=new Waiter(Config.TiempoSesion*60*1000, this);
        waiter.start();
    }

    public void touch()
    {
        waiter.touch();
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}