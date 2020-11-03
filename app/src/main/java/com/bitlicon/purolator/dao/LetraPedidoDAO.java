package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.DetallePedido;
import com.bitlicon.purolator.entities.LetraPedido;

import java.util.ArrayList;
import java.util.List;


public class LetraPedidoDAO {

    public static final String DIA = "Dia";
    public static final String PICKER = "Picker";
    public static final String IPEDIDO = "iPedido";
    public static final String NUMERO = "Numero";
    public static final String NUMERO_PEDIDO = "NumeroPedido";
    public static final String FECHA = "Fecha";
    private static String NOMBRE_TABLE = "LetraPedido";
    private Context context;

    public LetraPedidoDAO(Context context) {
        this.context = context;
    }

    public boolean registrarLetraPedido(LetraPedido letraPedido) {
        boolean registro = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PICKER, letraPedido.Picker);
            contentValues.put(IPEDIDO, letraPedido.iPedido);
            contentValues.put(NUMERO_PEDIDO, letraPedido.NumeroPedido);
            contentValues.put(DIA, letraPedido.Dia);
            contentValues.put(FECHA, letraPedido.Fecha);
            contentValues.put(NUMERO, letraPedido.Numero);
            db.openDataBase();
            db.getDataBase().insert(NOMBRE_TABLE, null, contentValues);
            registro = true;
        } catch (Exception ex) {
            Log.e("Registrar Letra", ex.getMessage(), ex);
            registro = false;
        } finally {
            db.close();
        }
        return registro;
    }

    public boolean verificarLetraPedido(LetraPedido letraPedido)
    {
        boolean encontrado = false;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE + " WHERE NumeroPedido=" + letraPedido.NumeroPedido + " AND Numero='" + letraPedido.Numero + "' LIMIT 1");
            String SQL = sb.toString();
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                encontrado = true;
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            encontrado = false;
        } finally {
            db.close();
        }
        return encontrado;
    }

    public LetraPedido obtenerLetraPedido(LetraPedido letraPedido)
    {
        LetraPedido letraPedidoEncontrado = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE + " WHERE NumeroPedido='" + letraPedido.NumeroPedido + "' AND Numero='" + letraPedido.Numero + "' LIMIT 1");
            String SQL = sb.toString();
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                letraPedidoEncontrado = new LetraPedido();
                letraPedidoEncontrado.iPedido = cursor.getInt(cursor.getColumnIndex(IPEDIDO));
                letraPedidoEncontrado.NumeroPedido = cursor.getString(cursor.getColumnIndex(NUMERO_PEDIDO));
                letraPedidoEncontrado.Numero = cursor.getInt(cursor.getColumnIndex(NUMERO));
                letraPedidoEncontrado.Dia = cursor.getInt(cursor.getColumnIndex(DIA));
                letraPedidoEncontrado.Fecha = cursor.getString(cursor.getColumnIndex(FECHA));
                letraPedidoEncontrado.Picker = cursor.getInt(cursor.getColumnIndex(PICKER)) == 1;
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            letraPedidoEncontrado = null;
        } finally {
            db.close();
        }
        return letraPedidoEncontrado;
    }

    public boolean actualizarDia(LetraPedido letraPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DIA, letraPedido.Dia);
            contentValues.put(FECHA, letraPedido.Fecha);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "NumeroPedido=? AND Numero=?", new String[]{letraPedido.NumeroPedido, String.valueOf(letraPedido.Numero)});
            estado = true;
        } catch (Exception ex) {
            Log.e("actualizarDia", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean eliminarLetraPedido(LetraPedido letraPedido)
    {
        boolean eliminar = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "Numero=? AND NumeroPedido=?", new String[]{String.valueOf(letraPedido.Numero), letraPedido.NumeroPedido});
            eliminar = true;
        }catch (Exception ex) {
            Log.e("Eliminar", ex.getMessage(), ex);
            eliminar = false;
        } finally {
            db.close();
        }
        return eliminar;
    }

    public boolean eliminarPorPedido(String NumeroPedido)
    {
        boolean eliminar = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "NumeroPedido=?", new String[]{NumeroPedido});
            eliminar = true;
        }catch (Exception ex) {
            eliminar = false;
        } finally {
            db.close();
        }
        return eliminar;
    }

    public List<LetraPedido> listarLetraPedido(String NumeroPedido)
    {
        List<LetraPedido> letraPedidos = null;
        LetraPedido letraPedido = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            letraPedidos = new ArrayList<>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" WHERE NumeroPedido ='" + NumeroPedido + "' ORDER BY Numero ASC ");
            String SQL = sb.toString();
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                letraPedido = new LetraPedido();
                letraPedido.iPedido = cursor.getInt(cursor.getColumnIndex(IPEDIDO));
                letraPedido.Numero = cursor.getInt(cursor.getColumnIndex(NUMERO));
                letraPedido.Dia = cursor.getInt(cursor.getColumnIndex(DIA));
                letraPedido.Fecha = cursor.getString(cursor.getColumnIndex(FECHA));
                letraPedido.Picker = cursor.getInt(cursor.getColumnIndex(PICKER)) == 1;
                letraPedidos.add(letraPedido);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            letraPedidos = null;
        } finally {
            db.close();
        }
        return letraPedidos;
    }

    public boolean eliminarLetraPedidoPorPedidoEnviado()
    {
        boolean eliminar = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" WHERE NumeroPedido IN (SELECT NumeroPedido FROM Pedido WHERE Enviado = 1)");
            String SQL = sb.toString();
            db.getDataBase().execSQL(SQL);
            eliminar = true;
        }catch (Exception ex) {
            Log.e("Eliminar LetPed", ex.getMessage(), ex);
            eliminar = false;
        } finally {
            db.close();
        }
        return eliminar;
    }

    public boolean actualizarLetraPedidoEnviado(String NumeroPedido, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NUMERO_PEDIDO, NumeroPedido);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarPedidoEnvi", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }
    public boolean eliminarLetraPedidoPorPedido(int iPedido)
    {
        boolean eliminar = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" WHERE iPedido=" + iPedido);
            String SQL = sb.toString();
            db.getDataBase().execSQL(SQL);
            eliminar = true;
        }catch (Exception ex) {
            eliminar = false;
        } finally {
            db.close();
        }
        return eliminar;
    }
}
