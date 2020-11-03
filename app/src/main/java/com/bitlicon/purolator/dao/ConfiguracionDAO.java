package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Configuracion;

import java.io.IOException;

/**
 * Created by EduardoAndrés on 02/12/2015.
 */
public class ConfiguracionDAO {

    private static String NOMBRE_TABLE = "Configuracion";
    private Context context;

    public static final String TIEMPOSESION = "TiempoSesion";
    public static final String TOPPRODUCTOS = "TopProductos";
    public static final String RESUEMN = "Resumen";
    public static final String PANTALLAINICIO = "PantallaInicio";
    public static final String FECHA = "Fecha";
    public static final String IGV = "Igv";

    public ConfiguracionDAO(Context context) {
        this.context = context;
    }

    public Configuracion Obtener()
    {
        Configuracion config = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        db.openDataBase();
        try {
            String SQL = "SELECT * FROM " + NOMBRE_TABLE + " LIMIT 1";
            cursor = db.getDataBase().rawQuery(SQL, null);
            if (cursor.moveToNext())
            {
                config = new Configuracion();
                config.PantallaInicio = cursor.getInt(cursor.getColumnIndex(PANTALLAINICIO));
                config.Resumen = cursor.getInt(cursor.getColumnIndex(RESUEMN));
                config.TiempoSesion = cursor.getInt(cursor.getColumnIndex(TIEMPOSESION));
                config.TopProductos = cursor.getInt(cursor.getColumnIndex(TOPPRODUCTOS));
                config.Fecha = cursor.getString(cursor.getColumnIndex(FECHA));
                config.IGV = cursor.getDouble(cursor.getColumnIndex(IGV));
            }
        } catch (Exception ex) {
            Log.e("ObtenerConfig DAO", ex.getMessage(), ex);
            config = null;
            throw ex;
        }
        return config;
    }

    public boolean ActualizarResumen(int Resumen)
    {
        boolean actualizado = false;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RESUEMN, Resumen);

            db.openDataBase();

            db.getDataBase().update(NOMBRE_TABLE, contentValues, null, null);
            actualizado = true;
        }
        catch (Exception ex)
        {
            Log.e("ActConfigResumen DAO", ex.getMessage(), ex);
            actualizado = false;
            throw ex;
        }
        finally
        {
            db.close();
        }

        return actualizado;
    }

    public boolean ActualizarPantallaInicio(int PantallaInicio)
    {
        boolean actualizado = false;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PANTALLAINICIO, PantallaInicio);

            db.openDataBase();

            db.getDataBase().update(NOMBRE_TABLE, contentValues, null,null);
            actualizado = true;
        }
        catch (Exception ex)
        {
            Log.e("ActConfigPantaIni DAO", ex.getMessage(), ex);
            actualizado = false;
            throw ex;
        }
        finally
        {
            db.close();
        }

        return actualizado;
    }

    public boolean ActualizarTopProductos(int TopProductos)
    {
        boolean actualizado = false;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TOPPRODUCTOS, TopProductos);

            db.openDataBase();

            db.getDataBase().update(NOMBRE_TABLE, contentValues, null,null);
            actualizado = true;
        }
        catch (Exception ex)
        {
            Log.e("ActConfigPantaIni DAO", ex.getMessage(), ex);
            actualizado = false;
            throw ex;
        }
        finally
        {
            db.close();
        }

        return actualizado;
    }

    public boolean ActualizarIgv(double igv)
    {
        boolean actualizado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(IGV, igv);
            db.openDataBase();
            db.getDataBase().update(NOMBRE_TABLE, contentValues, null,null);
            actualizado = true;
        }
        catch (Exception ex)
        {
            actualizado = false;
            throw ex;
        }
        finally
        {
            db.close();
        }

        return actualizado;
    }

    public boolean ActualizarTiempoSesion(int ActualizarTiempoSesion)
    {
        boolean actualizado = false;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TIEMPOSESION, ActualizarTiempoSesion);

            db.openDataBase();

            db.getDataBase().update(NOMBRE_TABLE, contentValues, null,null);
            actualizado = true;
        }
        catch (Exception ex)
        {
            Log.e("ActConfigTiempSesi DAO", ex.getMessage(), ex);
            actualizado = false;
            throw ex;
        }
        finally
        {
            db.close();
        }

        return actualizado;
    }
    public boolean ActualizarFecha(String Fecha)
    {
        boolean actualizado = false;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(FECHA, Fecha);

            db.openDataBase();

            db.getDataBase().update(NOMBRE_TABLE, contentValues, null,null);
            actualizado = true;
        }
        catch (Exception ex)
        {
            Log.e("ActConfigPantaIni DAO", ex.getMessage(), ex);
            actualizado = false;
            throw ex;
        }
        finally
        {
            db.close();
        }

        return actualizado;
    }

}
