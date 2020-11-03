package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Clase;
import com.bitlicon.purolator.entities.Descuento;
import com.bitlicon.purolator.entities.SubClase;

import java.util.ArrayList;

/**
 * Created by Lokerfy on 27/04/2016.
 */
public class DescuentoDAO {

    public static final String DESCUENTO_ID = "DescuentoID";
    public static final String DESCRIPCION = "Descripcion";

    private static String NOMBRE_TABLE = "Descuento";
    private Context context;

    public DescuentoDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(Descuento descuento) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DESCUENTO_ID, descuento.DescuentoID);
        contentValues.put(DESCRIPCION, descuento.Descripcion);
        return contentValues;
    }

    private static Descuento getDescuento(Cursor cursor) {
        Descuento descuento = new Descuento();
        descuento.DescuentoID = cursor.getString(cursor.getColumnIndex(DESCUENTO_ID));
        descuento.Descripcion = cursor.getString(cursor.getColumnIndex(DESCRIPCION));
        return descuento;
    }

    public boolean eliminar()
    {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "1==1", null);
            estado = true;
        }
        catch (Exception ex)
        {
            Log.e("EliminarDescuento DAO", ex.getMessage(), ex);
        }
        finally {
            db.close();
        }
        return estado;
    }

    public ArrayList<Descuento> listarDescuentos()
    {
        ArrayList<Descuento> lista = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            lista = new ArrayList<Descuento>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" ORDER BY DescuentoID ASC");

            String SQL = sb.toString();
            Log.d("DescuentoDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);

            while (cursor.moveToNext()) {
                lista.add(getDescuento(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            lista = null;
        }
        finally
        {
            db.close();
        }
        return lista;
    }

}
