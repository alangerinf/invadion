package com.bitlicon.purolator.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AppStatus {

    /**
     * Variable que dice si esta conectado por WIFI
     */
    public static final int TYPE_WIFI = 1;
    /**
     * Variable que dice si esta conectado por 3G / 4G
     */
    public static final int TYPE_MOBILE = 2;
    /**
     * Variable que dice si no esta conectado
     */
    public static final int TYPE_NOT_CONNECTED = 0;
    /**
     * Variable que genera una instancia local
     */
    private static AppStatus instance = new AppStatus();
    /**
     * Contexto de la aplicacion
     */
    private static Context context;
    /**
     * Variable que maneja la conectividad del dispositivo
     */
    private ConnectivityManager connectivityManager;
    /**
     * Variable que maneja la informacion de la red
     */
    private NetworkInfo networkInfo;
    /**
     * Variable que valida si esta o no conectado
     */
    private boolean connected = false;

    /**
     * Genera un Singleton de la Clase
     *
     * @param ctx contexto de la aplicacion
     * @return la clase instanciada una sola vez
     */
    public static AppStatus getInstance(Context ctx) {
        context = ctx;
        return instance;
    }

    /**
     * Metodo que obtiene el tipo de conexion del dispositivo
     *
     * @return el tipo de conexion del dispositivo
     * @author Diana
     */
    public int getConnectivityStatus() {
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if (null != networkInfo && networkInfo.isAvailable() && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
        } catch (Exception ex) {
            return TYPE_NOT_CONNECTED;
        }
        return TYPE_NOT_CONNECTED;
    }

    /**
     * Metodo que obtiene si el dispositivo esta conectado o no
     *
     * @return el estado actual del dispositivo si esta conectado o no
     * @author Diana
     */
    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            Log.e("CheckConnectivity", e.getMessage());
            Log.e("connectivity", e.toString());
        }
        return connected;
    }

}