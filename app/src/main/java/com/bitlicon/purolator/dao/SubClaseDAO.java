package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Producto;
import com.bitlicon.purolator.entities.SubClase;

import java.util.ArrayList;

/**
 * Created by EduardoAndrés on 11/12/2015.
 */
public class SubClaseDAO {

    public static final String CODIGO = "Codigo";
    public static final String NOMBRE = "Nombre";

    private static String NOMBRE_TABLE = "SubClase";
    private Context context;

    public SubClaseDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(SubClase subClase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CODIGO, subClase.Codigo);
        contentValues.put(NOMBRE, subClase.Nombre);
        return contentValues;
    }

    private static SubClase getProducto(Cursor cursor) {
        SubClase subClase = new SubClase();
        subClase.Codigo = cursor.getString(cursor.getColumnIndex(CODIGO));
        subClase.Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
        return subClase;
    }

    public boolean eliminarSubClase()
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
            Log.e("EliminarSubClase DAO", ex.getMessage(), ex);
        }
        finally {
            db.close();
        }
        return estado;
    }

    public ArrayList<SubClase> listarSubClases()
    {
        ArrayList<SubClase> lista = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            lista = new ArrayList<SubClase>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" ORDER BY Nombre ASC");

            String SQL = sb.toString();
            Log.d("SubClaseDAO", SQL);
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
