package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Vendedor;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

public class VendedorDAO {
    public static final String VENDEDOR_ID = "VendedorID";
    public static final String DISTRIBUIDOR_ID = "DistribuidorID";
    public static final String LINEA_VENTA = "LineaVenta";
    public static final String PASSWORD = "Password";
    public static final String NOMBRE = "Nombre";
    public static final String FECHA_CREACION = "FechaCreacion";
    public static final String FECHA_SINCRO = "FechaSincro";
    public static final String USUARIO = "Usuario";
    public static final String LINEA_1 = "Linea1";
    public static final String LINEA_2 = "Linea2";
    public static final String CUOTA_LINEA_1 = "CuotaLinea1";
    public static final String CUOTA_LINEA_2 = "CuotaLinea2";
    public static final String RECORDAR = "Recordar";
    private static String NOMBRE_TABLE = "Vendedor";
    private Context context;

    public VendedorDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(Vendedor vendedor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USUARIO, vendedor.Usuario);
        contentValues.put(LINEA_1, vendedor.Linea1);
        contentValues.put(LINEA_2, vendedor.Linea2);
        contentValues.put(PASSWORD, vendedor.Password);
        contentValues.put(NOMBRE, vendedor.Nombre);
        contentValues.put(FECHA_SINCRO, vendedor.FechaSincro);
        contentValues.put(RECORDAR, vendedor.Recordar);
        contentValues.put(CUOTA_LINEA_1, vendedor.CuotaPurolator);
        contentValues.put(CUOTA_LINEA_2, vendedor.CuotaFiltech);
        return contentValues;
    }

    private Vendedor getVendedor(Cursor cursor) {
        Vendedor vendedor = new Vendedor();
        vendedor.Usuario = cursor.getString(cursor.getColumnIndex(USUARIO));
        vendedor.Linea1 = cursor.getString(cursor.getColumnIndex(LINEA_1));
        vendedor.Linea2 = cursor.getString(cursor.getColumnIndex(LINEA_2));
        vendedor.Password = cursor.getString(cursor.getColumnIndex(PASSWORD));
        vendedor.Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
        vendedor.FechaSincro = cursor.getString(cursor.getColumnIndex(FECHA_SINCRO));
        vendedor.Recordar = cursor.getInt(cursor.getColumnIndex(RECORDAR));
        vendedor.CuotaPurolator = cursor.getDouble(cursor.getColumnIndex(CUOTA_LINEA_1));
        vendedor.CuotaFiltech = cursor.getDouble(cursor.getColumnIndex(CUOTA_LINEA_2));

        return vendedor;
    }

    public boolean registrarVendedor(Vendedor vendedor) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().insert(NOMBRE_TABLE, null, getContentValues(vendedor));
            estado = true;
        } catch (Exception ex) {
            Log.e("RegistrarVendedor", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public Vendedor buscarVendedor(String usuario, String clave, String fecha) {
        Vendedor vendedor = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            String SQL = "SELECT * FROM " + NOMBRE_TABLE + " WHERE Usuario=? AND Password=? AND FechaSincro=?";
            cursor = db.getDataBase().rawQuery(SQL, new String[]{usuario, clave, fecha});
            while (cursor.moveToNext()) {
                vendedor = getVendedor(cursor);
            }
        } catch (Exception ex) {
            Log.e("RegistrarCliente DAO", ex.getMessage(), ex);
            vendedor = null;
        } finally {
            db.close();
        }
        return vendedor;
    }

    public long existeVendedor() {
        long cantidad = 0;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            String SQL = "SELECT count(1) as Cantidad FROM " + NOMBRE_TABLE;
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                cantidad = cursor.getLong(cursor.getColumnIndex("Cantidad"));
            }
        } catch (Exception ex) {
            LOGE(getClass(), ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return cantidad;
    }



    public Vendedor buscarVendedor(String usuario) {
        Vendedor vendedor = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            String SQL = "SELECT * FROM " + NOMBRE_TABLE + " WHERE Usuario=?";
            cursor = db.getDataBase().rawQuery(SQL, new String[]{usuario});
            while (cursor.moveToNext()) {
                vendedor = getVendedor(cursor);
            }
        } catch (Exception ex) {
            Log.e("Buscar Vendedor", ex.getMessage(), ex);
            vendedor = null;
        } finally {
            db.close();
        }
        return vendedor;
    }

    public Vendedor buscarVendedor() {
        Vendedor vendedor = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            String SQL = "SELECT * FROM " + NOMBRE_TABLE;
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                vendedor = getVendedor(cursor);
            }
        } catch (Exception ex) {
            Log.e("Buscar Vendedor", ex.getMessage(), ex);
            vendedor = null;
        } finally {
            db.close();
        }
        return vendedor;
    }

    public boolean eliminarVendedor() {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "1==1", null);
            estado = true;
        } catch (Exception ex) {
            Log.e("EliminarVendedor DAO", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }


}
