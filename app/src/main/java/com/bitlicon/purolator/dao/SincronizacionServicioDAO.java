package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.SincronizacionServicio;


/**
 * Created by dianewalls on 17/07/2015.
 */
public class SincronizacionServicioDAO {


    public static final String ENTIDAD = "Entidad";
    public static final String ESTADO = "Estado";
    public static final String FECHA_SINCRONIZACION = "FechaSincronizacion";
    public static final String MENSAJE = "Mensaje";
    public static final String SERVICIO = "Servicio";
    public static final String VENDEDOR = "Vendedor";
    private static final String NOMBRE_TABLE = "SincronizacionServicio";
    private Context context;

    public SincronizacionServicioDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(SincronizacionServicio sincronizacionServicio) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ENTIDAD, sincronizacionServicio.Entidad);
        contentValues.put(ESTADO, sincronizacionServicio.Estado);
        contentValues.put(FECHA_SINCRONIZACION, sincronizacionServicio.FechaSincronizacion);
        contentValues.put(MENSAJE, sincronizacionServicio.Mensaje);
        contentValues.put(SERVICIO, sincronizacionServicio.Servicio);
        contentValues.put(VENDEDOR, sincronizacionServicio.Vendedor);
        return contentValues;
    }

    public long registrar(SincronizacionServicio sincronizacionServicio) {
        long row;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            row = db.getDataBase().insert(NOMBRE_TABLE, null, getContentValues(sincronizacionServicio));
        } catch (Exception ex) {
            row = -1;
        } finally {
            db.close();
        }

        return row;
    }

    public boolean eliminar(String entidad) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "Entidad=?", new String[]{entidad});
            estado = true;
        } catch (Exception ex) {
            Log.e("EliminarEntidad DAO", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }

    public SincronizacionServicio buscarServicio(String entidad) {
        SincronizacionServicio sincronizacionServicio = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            String SQL = "SELECT * FROM " + NOMBRE_TABLE + " WHERE Entidad=?";
            cursor = db.getDataBase().rawQuery(SQL, new String[]{entidad});
            while (cursor.moveToNext()) {
                sincronizacionServicio = getSincronizacionServicio(cursor);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            sincronizacionServicio = null;
        } finally {
            db.close();
        }
        return sincronizacionServicio;
    }

    private SincronizacionServicio getSincronizacionServicio(Cursor cursor) {
        SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
        sincronizacionServicio.Entidad = cursor.getString(cursor.getColumnIndex(ENTIDAD));
        sincronizacionServicio.Estado = cursor.getInt(cursor.getColumnIndex(ESTADO));
        sincronizacionServicio.FechaSincronizacion = cursor.getString(cursor.getColumnIndex(FECHA_SINCRONIZACION));
        sincronizacionServicio.Mensaje = cursor.getString(cursor.getColumnIndex(MENSAJE));
        sincronizacionServicio.Servicio = cursor.getString(cursor.getColumnIndex(SERVICIO));
        sincronizacionServicio.Vendedor = cursor.getString(cursor.getColumnIndex(VENDEDOR));

        return sincronizacionServicio;
    }

}
