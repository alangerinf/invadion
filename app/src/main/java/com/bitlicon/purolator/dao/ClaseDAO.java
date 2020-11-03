package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Clase;
import com.bitlicon.purolator.entities.SubClase;

import java.util.ArrayList;

/**
 * Created by EduardoAndrés on 11/12/2015.
 */
public class ClaseDAO {

    public static final String CODIGO = "Codigo";
    public static final String NOMBRE = "Nombre";

    private static String NOMBRE_TABLE = "Clase";
    private Context context;

    public ClaseDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(Clase clase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CODIGO, clase.Codigo);
        contentValues.put(NOMBRE, clase.Nombre);
        return contentValues;
    }

    private static Clase getProducto(Cursor cursor) {
        Clase clase = new Clase();
        clase.Codigo = cursor.getString(cursor.getColumnIndex(CODIGO));
        clase.Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
        return clase;
    }

    public boolean eliminarClases()
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
            Log.e("EliminarClase DAO", ex.getMessage(), ex);
        }
        finally {
            db.close();
        }
        return estado;
    }

    public ArrayList<Clase> listarClases()
    {
        ArrayList<Clase> lista = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            lista = new ArrayList<Clase>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" ORDER BY Nombre ASC");

            String SQL = sb.toString();
            Log.d("ClaseDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);

            while (cursor.moveToNext()) {
                lista.add(getProducto(cursor));
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
