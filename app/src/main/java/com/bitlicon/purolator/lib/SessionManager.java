package com.bitlicon.purolator.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bitlicon.purolator.util.Util;

import java.util.Date;


public class SessionManager {

    private static final String PREF_NAME = "Android";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_IDUSUARIO = "idusuario";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_PUROLATOR = "PUR";
    private static final String KEY_FILTECH = "FIL";


    //private static final String KEY_LINEA = "linea";
    private static final String KEY_FECHA_SINCRO = "fechaSincro";
    //private static final String KEY_LOGO = "fechaSincro";

    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String idusuario, String nombre, String purolator, String filtech) {
        editor.putBoolean(IS_LOGIN, false);
        editor.putString(KEY_IDUSUARIO, idusuario);
        editor.putString(KEY_NOMBRE, nombre);
        editor.putString(KEY_PUROLATOR, purolator);
        editor.putString(KEY_FILTECH, filtech);
        editor.putString(KEY_FECHA_SINCRO, Util.formatoFechaQuery(new Date()));

        editor.commit();
    }

/*    public void createLoginSession(String idusuario, String nombre, String idLinea, int idLogo) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_IDUSUARIO, idusuario);
        editor.putString(KEY_NOMBRE, nombre);
        editor.putString(KEY_FECHA_SINCRO, Util.formatoFechaQuery(new Date()));
        editor.putString(KEY_LINEA, idLinea);
        editor.putInt(KEY_LOGO, idLogo);
        editor.commit();
    }*/

    public boolean isLoggedIn() {
        String fechaSincro = String.valueOf(pref.getAll().get(KEY_FECHA_SINCRO));
        return pref.getBoolean(IS_LOGIN, false) && fechaSincro != null && fechaSincro == Util.formatoFechaQuery(new Date());
    }

    public String getNombreUsuario() {
        return pref.getString(KEY_NOMBRE, null);
    }

   /* public String getIdLinea() {
        return pref.getString(KEY_LINEA, null);
    }*/

/*
    public int getIdLogo() {
        return pref.getInt(KEY_LOGO, -1);
    }
*/

    public String getIdUsuario() {
        return pref.getString(KEY_IDUSUARIO, null);
    }

    public String getPurolator() {
        return pref.getString(KEY_PUROLATOR, null);
    }

    public String getFiltech() {
        return pref.getString(KEY_FILTECH, null);
    }



    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

}
