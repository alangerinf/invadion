package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.DetallePedido;
import com.bitlicon.purolator.entities.Pedido;

import java.util.ArrayList;
import java.util.List;


public class DetallePedidoDAO {

    public static final String PRODUCTO_ID = "ProductoID";
    public static final String PRODUCTO = "Producto";
    public static final String IPEDIDO = "iPedido";
    public static final String NUMERO_PEDIDO = "NumeroPedido";
    public static final String CANTIDAD = "Cantidad";
    public static final String PRECIO = "Precio";
    public static final String DESCUENTO1 = "Descuento1";
    public static final String DESCUENTO2 = "Descuento2";
    public static final String DESCUENTO11 = "Descuento11";
    public static final String DESCUENTO12 = "Descuento12";

    private static String NOMBRE_TABLE = "DetallePedido";
    private Context context;

    public DetallePedidoDAO(Context context) {
        this.context = context;
    }

    public boolean registrarDetallePedido(DetallePedido detallePedido) {
        boolean registro = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PRODUCTO_ID, detallePedido.ProductoID);
            contentValues.put(IPEDIDO, detallePedido.iPedido);
            contentValues.put(CANTIDAD, detallePedido.Cantidad);
            contentValues.put(PRECIO, detallePedido.Precio);
            contentValues.put(DESCUENTO1, detallePedido.Descuento1);
            contentValues.put(DESCUENTO2, detallePedido.Descuento2);
            contentValues.put(DESCUENTO11, detallePedido.Descuento11);
            contentValues.put(DESCUENTO12, detallePedido.Descuento12);
            db.openDataBase();
            db.getDataBase().insert(NOMBRE_TABLE, null, contentValues);
            registro = true;
        } catch (Exception ex) {
            Log.e("RegistrarDetPedido", ex.getMessage(), ex);
            registro = false;
        } finally {
            db.close();
        }
        return registro;
    }

    public boolean obtenerDetallePedido(DetallePedido detallePedido)
    {
        boolean encontrado = false;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE + " WHERE iPedido=" + detallePedido.iPedido + " AND ProductoID='" + detallePedido.ProductoID + "' LIMIT 1");
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

    public boolean actualizarCantidad(DetallePedido detallePedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CANTIDAD, detallePedido.Cantidad);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=? AND ProductoID=?", new String[]{String.valueOf(detallePedido.iPedido), detallePedido.ProductoID});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarCantidad", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarDetallePedido(DetallePedido detallePedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CANTIDAD, detallePedido.Cantidad);
            contentValues.put(DESCUENTO1, detallePedido.Descuento1);
            contentValues.put(DESCUENTO2, detallePedido.Descuento2);
            contentValues.put(DESCUENTO11, detallePedido.Descuento11);
            contentValues.put(DESCUENTO12, detallePedido.Descuento12);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=? AND ProductoID=?", new String[]{String.valueOf(detallePedido.iPedido), detallePedido.ProductoID});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarCantidad", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarCantidadYPrecio(DetallePedido detallePedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CANTIDAD, detallePedido.Cantidad);
            contentValues.put(PRECIO, detallePedido.Precio);
            contentValues.put(DESCUENTO1, detallePedido.Descuento1);
            contentValues.put(DESCUENTO2, detallePedido.Descuento2);
            contentValues.put(DESCUENTO11, detallePedido.Descuento11);
            contentValues.put(DESCUENTO12, detallePedido.Descuento12);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=? AND ProductoID=?", new String[]{String.valueOf(detallePedido.iPedido), detallePedido.ProductoID});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarCantidad", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarPrecio(DetallePedido detallePedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PRECIO, detallePedido.Precio);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=? AND ProductoID=?", new String[]{String.valueOf(detallePedido.iPedido), detallePedido.ProductoID});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarCantidad", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public List<DetallePedido> listarDetallePedido(int iPedido)
    {
        List<DetallePedido> detallePedidos = null;
        DetallePedido detallePedido = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            detallePedidos = new ArrayList<>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT dp.*, p.Nombre as Producto FROM ");
            sb.append(NOMBRE_TABLE + " dp INNER JOIN Producto p ON p.ProductoID=dp.ProductoID ");
            sb.append(" WHERE iPedido ='" + iPedido + "' ORDER BY p.Nombre ASC ");
            String SQL = sb.toString();
            Log.d("PedidoDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                detallePedido = new DetallePedido();
                detallePedido.iPedido = cursor.getInt(cursor.getColumnIndex(IPEDIDO));
                detallePedido.Cantidad = cursor.getInt(cursor.getColumnIndex(CANTIDAD));
                detallePedido.Descuento1 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO1));
                detallePedido.Descuento2 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO2));
                detallePedido.Descuento11 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO11));
                detallePedido.Descuento12 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO12));
                detallePedido.Precio = cursor.getDouble(cursor.getColumnIndex(PRECIO));
                detallePedido.ProductoID = cursor.getString(cursor.getColumnIndex(PRODUCTO_ID));
                detallePedido.Producto = cursor.getString(cursor.getColumnIndex(PRODUCTO));
                detallePedidos.add(detallePedido);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            detallePedidos = null;
        } finally {
            db.close();
        }
        return detallePedidos;
    }

    public List<DetallePedido> listarDetallePedidoPorNumero(String NumeroPedido)
    {
        List<DetallePedido> detallePedidos = null;
        DetallePedido detallePedido = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            detallePedidos = new ArrayList<>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT dp.*, p.Nombre as Producto FROM ");
            sb.append(NOMBRE_TABLE + " dp INNER JOIN Producto p ON p.ProductoID=dp.ProductoID ");
            sb.append(" WHERE NumeroPedido ='" + NumeroPedido + "' ORDER BY p.Nombre ASC ");
            String SQL = sb.toString();
            Log.d("PedidoDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                detallePedido = new DetallePedido();
                detallePedido.iPedido = cursor.getInt(cursor.getColumnIndex(IPEDIDO));
                detallePedido.Cantidad = cursor.getInt(cursor.getColumnIndex(CANTIDAD));
                detallePedido.Precio = cursor.getDouble(cursor.getColumnIndex(PRECIO));
                detallePedido.ProductoID = cursor.getString(cursor.getColumnIndex(PRODUCTO_ID));
                detallePedido.NumeroPedido = cursor.getString(cursor.getColumnIndex(NUMERO_PEDIDO));
                detallePedido.Producto = cursor.getString(cursor.getColumnIndex(PRODUCTO));
                detallePedido.Descuento1 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO1));
                detallePedido.Descuento2 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO2));
                detallePedido.Descuento11 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO11));
                detallePedido.Descuento12 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO12));
                detallePedidos.add(detallePedido);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            detallePedidos = null;
        } finally {
            db.close();
        }
        return detallePedidos;
    }

    public boolean eliminarDetallePedido(DetallePedido detallePedido)
    {
        boolean eliminar = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "ProductoID=? AND iPedido=?", new String[]{detallePedido.ProductoID, String.valueOf(detallePedido.iPedido)});
            eliminar = true;
        }catch (Exception ex) {
            Log.e("RegistrarDetPedido", ex.getMessage(), ex);
            eliminar = false;
        } finally {
            db.close();
        }
        return eliminar;
    }

    public boolean eliminarDetallePedidoPorPedidoEnviado()
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
            Log.e("Eliminar DetPe", ex.getMessage(), ex);
            eliminar = false;
        } finally {
            db.close();
        }
        return eliminar;
    }

    public boolean actualizarDetallePedidoEnviado(String NumeroPedido, int iPedido) {
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

    public boolean eliminarDetallePedidoPorPedido(int iPedido)
    {
        boolean eliminar = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" WHERE iPedido="+iPedido);
            String SQL = sb.toString();
            db.getDataBase().execSQL(SQL);
            eliminar = true;
        }catch (Exception ex) {
            Log.e("Eliminar DetPe", ex.getMessage(), ex);
            eliminar = false;
        } finally {
            db.close();
        }
        return eliminar;
    }
}
