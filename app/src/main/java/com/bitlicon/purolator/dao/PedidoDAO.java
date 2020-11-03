package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public static final String CLIENTE_ID = "ClienteID";
    public static final String TOTAL_IMPORTE = "TotalImporte";
    public static final String TOTAL_IMPUESTOS = "TotalImpuestos";
    public static final String TOTAL_DESCUENTO = "TotalDescuento";
    public static final String DESCUENTO_1 = "Descuento1";
    public static final String DESCUENTO_2 = "Descuento2";
    public static final String DESCUENTO_3 = "Descuento3";
    public static final String DESCUENTO_4 = "Descuento4";
    public static final String DESCUENTO_5 = "Descuento5";

    public static final String DESCUENTO_1_VALOR = "Descuento1Valor";
    public static final String DESCUENTO_2_VALOR = "Descuento2Valor";
    public static final String DESCUENTO_3_VALOR = "Descuento3Valor";

    public static final String FECHA_CREACION_PEDIDO = "FechaCreacionPedido";
    public static final String TERMINO_VENTA = "TerminoVenta";
    public static final String NUMERO_PEDIDO = "NumeroPedido";
    public static final String ENVIADO = "Enviado";
    public static final String VENDEDOR_ID = "VendedorID";
    public static final String OBSERVACION = "Observacion";
    public static final String IPEDIDO = "iPedido";
    private static String NOMBRE_TABLE = "Pedido";
    private Context context;

    public PedidoDAO(Context context) {
        this.context = context;
    }

    public int registrarPedido(Pedido pedido) {
        int iPedido;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(CLIENTE_ID, pedido.ClienteID);
            contentValues.put(VENDEDOR_ID, pedido.VendedorID);
            contentValues.put(FECHA_CREACION_PEDIDO, pedido.FechaCreacionPedido);
            contentValues.put(TERMINO_VENTA,pedido.TerminoVenta);
            contentValues.put(TOTAL_DESCUENTO,pedido.TotalDescuento);
            contentValues.put(TOTAL_IMPORTE,pedido.TotalImporte);
            contentValues.put(TOTAL_IMPUESTOS,pedido.TotalImpuestos);
            contentValues.put(DESCUENTO_1,pedido.Descuento1);
            contentValues.put(DESCUENTO_2,pedido.Descuento2);
            contentValues.put(DESCUENTO_3,pedido.Descuento3);
            contentValues.put(DESCUENTO_4,pedido.Descuento4);
            contentValues.put(DESCUENTO_5,pedido.Descuento5);
            contentValues.put(DESCUENTO_1_VALOR,0);
            contentValues.put(DESCUENTO_2_VALOR,0);
            contentValues.put(DESCUENTO_3_VALOR,0);

            contentValues.put(OBSERVACION,pedido.Observacion);
            db.openDataBase();
            iPedido = (int) db.getDataBase().insert(NOMBRE_TABLE, null, contentValues);
        } catch (Exception ex) {
            Log.e("RegistrarPedido DAO", ex.getMessage(), ex);
            iPedido = -1;
        } finally {
            db.close();
        }
        return iPedido;
    }

    public boolean actualizarTerminoVenta(String TerminoVenta, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TERMINO_VENTA, TerminoVenta);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarTeminoVent", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarTotalImporte(double TotalImporte, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TOTAL_IMPORTE, TotalImporte);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarTeminoVent", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarVendedor(String VendedorId, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(VENDEDOR_ID, VendedorId);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarVendedor", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarObservacion(String Observacion, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(OBSERVACION, Observacion);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarObservacion", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }
    public boolean actualizarTotalDescuento(double TotalDescuento, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TOTAL_DESCUENTO, TotalDescuento);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarObservacion", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }
    public boolean actualizarTotalImpuestos(double TotalImpuestos, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TOTAL_IMPUESTOS, TotalImpuestos);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActuImpuestos", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarDescuento1Valor(double descuento1, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DESCUENTO_1_VALOR, descuento1);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActuImpuesto d1", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarDescuento2Valor(double descuento2, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DESCUENTO_2_VALOR, descuento2);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActuImpuesto d2", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarDescuento3Valor(double descuento3, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DESCUENTO_3_VALOR, descuento3);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActuImpuesto d3", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public List<Pedido> listarPedidos(String ClientID)
    {
        List<Pedido> pedidos = null;
        Pedido pedido = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            pedidos = new ArrayList<>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" WHERE ClienteID='" + ClientID + "' ORDER BY FechaCreacionPedido DESC");
            String SQL = sb.toString();
            Log.d("PedidoDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                pedido = new Pedido();
                pedido.iPedido = cursor.getInt(cursor.getColumnIndex(IPEDIDO));
                pedido.TotalImporte = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPORTE));
                pedido.TotalImpuestos = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPUESTOS));
                pedido.TotalDescuento = cursor.getDouble(cursor.getColumnIndex(TOTAL_DESCUENTO));
                pedido.NumeroPedido = cursor.getString(cursor.getColumnIndex(NUMERO_PEDIDO));
                pedido.FechaCreacionPedido = cursor.getString(cursor.getColumnIndex(FECHA_CREACION_PEDIDO));
                pedido.TerminoVenta = cursor.getString(cursor.getColumnIndex(TERMINO_VENTA));
                pedido.VendedorID = cursor.getString(cursor.getColumnIndex(VENDEDOR_ID));
                pedido.Observacion = cursor.getString(cursor.getColumnIndex(OBSERVACION));
                pedido.Enviado = cursor.getInt(cursor.getColumnIndex(ENVIADO)) == 1;

                pedido.Descuento1 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_1)) == 1;
                pedido.Descuento2 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_2)) == 1;
                pedido.Descuento3 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_3)) == 1;
                pedido.Descuento4 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_4)) == 1;
                pedido.Descuento5 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_5)) == 1;

                pedido.Descuento1Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_1_VALOR));
                pedido.Descuento2Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_2_VALOR));
                pedido.Descuento3Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_3_VALOR));

                if(pedido.NumeroPedido == null)
                {
                    pedido.NumeroPedido = String.valueOf(pedido.iPedido);
                }
                pedidos.add(pedido);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            pedidos = null;
        } finally {
            db.close();
        }
        return pedidos;
    }

    public List<Pedido> listarPedidosPorVendedorYEnviados(String ClientID, String VendedorID)
    {
        List<Pedido> pedidos = null;
        Pedido pedido = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            pedidos = new ArrayList<>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" WHERE ClienteID='" + ClientID + "' AND Enviado=1 AND VendedorID='" + VendedorID + "' ORDER BY FechaCreacionPedido DESC LIMIT 3");
            String SQL = sb.toString();

            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                pedido = new Pedido();
                pedido.iPedido = cursor.getInt(cursor.getColumnIndex(IPEDIDO));
                pedido.TotalImporte = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPORTE));
                pedido.TotalImpuestos = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPUESTOS));
                pedido.TotalDescuento = cursor.getDouble(cursor.getColumnIndex(TOTAL_DESCUENTO));
                pedido.NumeroPedido = cursor.getString(cursor.getColumnIndex(NUMERO_PEDIDO));
                pedido.FechaCreacionPedido = cursor.getString(cursor.getColumnIndex(FECHA_CREACION_PEDIDO));
                pedido.TerminoVenta = cursor.getString(cursor.getColumnIndex(TERMINO_VENTA));
                pedido.VendedorID = cursor.getString(cursor.getColumnIndex(VENDEDOR_ID));
                pedido.Observacion = cursor.getString(cursor.getColumnIndex(OBSERVACION));
                pedido.Enviado = cursor.getInt(cursor.getColumnIndex(ENVIADO)) == 1;

                pedido.Descuento1 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_1)) == 1;
                pedido.Descuento2 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_2)) == 1;
                pedido.Descuento3 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_3)) == 1;
                pedido.Descuento4 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_4)) == 1;
                pedido.Descuento5 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_5)) == 1;

                pedido.Descuento1Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_1_VALOR));
                pedido.Descuento2Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_2_VALOR));
                pedido.Descuento3Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_3_VALOR));

                if(pedido.NumeroPedido == null)
                {
                    pedido.NumeroPedido = String.valueOf(pedido.iPedido);
                }
                pedidos.add(pedido);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            pedidos = null;
        } finally {
            db.close();
        }
        return pedidos;
    }

    public List<Pedido> listarPedidosPorMonto(String ClientID)
    {
        List<Pedido> pedidos = null;
        Pedido pedido = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            pedidos = new ArrayList<>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" WHERE ClienteID='" + ClientID + "' ORDER BY TotalImporte DESC");
            String SQL = sb.toString();

            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                pedido = new Pedido();
                pedido.iPedido = cursor.getInt(cursor.getColumnIndex(IPEDIDO));
                pedido.TotalImporte = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPORTE));

                pedido.TotalImpuestos = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPUESTOS));

                pedido.TotalDescuento = cursor.getDouble(cursor.getColumnIndex(TOTAL_DESCUENTO));
                pedido.NumeroPedido = cursor.getString(cursor.getColumnIndex(NUMERO_PEDIDO));
                pedido.TerminoVenta = cursor.getString(cursor.getColumnIndex(TERMINO_VENTA));
                pedido.VendedorID = cursor.getString(cursor.getColumnIndex(VENDEDOR_ID));
                pedido.Observacion = cursor.getString(cursor.getColumnIndex(OBSERVACION));
                pedido.Enviado = cursor.getInt(cursor.getColumnIndex(ENVIADO)) == 1;

                pedido.Descuento1 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_1)) == 1;
                pedido.Descuento2 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_2)) == 1;
                pedido.Descuento3 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_3)) == 1;
                pedido.Descuento4 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_4)) == 1;
                pedido.Descuento5 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_5)) == 1;

                pedido.Descuento1Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_1_VALOR));
                pedido.Descuento2Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_2_VALOR));
                pedido.Descuento3Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_3_VALOR));

                if(pedido.NumeroPedido == null)
                {
                    pedido.NumeroPedido = String.valueOf(pedido.iPedido);
                }
                pedidos.add(pedido);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            pedidos = null;
        } finally {
            db.close();
        }
        return pedidos;
    }

    public Pedido obtenerPedido(int iPedido)
    {
        Pedido pedido = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            pedido = new Pedido();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" WHERE iPedido=" + iPedido + " LIMIT 1");
            String SQL = sb.toString();
            Log.d("PedidoDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                pedido.iPedido = cursor.getInt(cursor.getColumnIndex(IPEDIDO));
                pedido.TotalImporte = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPORTE));
                pedido.TotalImpuestos = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPUESTOS));
                pedido.TotalDescuento = cursor.getDouble(cursor.getColumnIndex(TOTAL_DESCUENTO));
                pedido.NumeroPedido = cursor.getString(cursor.getColumnIndex(NUMERO_PEDIDO));
                pedido.TerminoVenta = cursor.getString(cursor.getColumnIndex(TERMINO_VENTA));
                pedido.Observacion = cursor.getString(cursor.getColumnIndex(OBSERVACION));
                pedido.VendedorID = cursor.getString(cursor.getColumnIndex(VENDEDOR_ID));
                pedido.ClienteID = cursor.getString(cursor.getColumnIndex(CLIENTE_ID));
                pedido.Enviado = cursor.getInt(cursor.getColumnIndex(ENVIADO)) == 1;
                pedido.Descuento1 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_1)) == 1;
                pedido.Descuento2 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_2)) == 1;
                pedido.Descuento3 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_3)) == 1;
                pedido.Descuento4 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_4)) == 1;
                pedido.Descuento5 = cursor.getInt(cursor.getColumnIndex(DESCUENTO_5)) == 1;

                pedido.Descuento1Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_1_VALOR));
                pedido.Descuento2Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_2_VALOR));
                pedido.Descuento3Valor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_3_VALOR));

                if(pedido.NumeroPedido == null)
                {
                    pedido.NumeroPedido = String.valueOf(pedido.iPedido);
                }
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            pedido = null;
        } finally {
            db.close();
        }
        return pedido;
    }

    public boolean actualizarNumeroPedido(String NumeroPedido, int iPedido) {
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

    public boolean actualizarPedidoEnviado(String NumeroPedido, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NUMERO_PEDIDO, NumeroPedido);
            contentValues.put(ENVIADO, true);
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


    public boolean actualizarDescuento1(boolean descuento1, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DESCUENTO_1, descuento1);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarDescuento1", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarDescuento2(boolean descuento2, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DESCUENTO_2, descuento2);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarDescuento2", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarDescuento3(boolean descuento3, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DESCUENTO_3, descuento3);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarDescuento3", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean actualizarDescuento4(boolean descuento4, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DESCUENTO_4, descuento4);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarDescuento4", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }


    public boolean actualizarDescuento5(boolean descuento5, int iPedido) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DESCUENTO_5, descuento5);
            db.getDataBase().update(NOMBRE_TABLE, contentValues, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarDescuento5", ex.getMessage(), ex);
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean eliminarPedidos() {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "1==1 AND Enviado=1", null);
            estado = true;
        } catch (Exception ex) {
            Log.e("Eliminar Pedi", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean eliminarPedido(int iPedido)
    {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "iPedido=?", new String[]{String.valueOf(iPedido)});
            estado = true;
        } catch (Exception ex) {
            Log.e("Eliminar Pedi", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }
}
