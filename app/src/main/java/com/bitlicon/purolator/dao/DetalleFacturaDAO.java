package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.bitlicon.purolator.entities.DetalleFactura;
import java.util.ArrayList;

public class DetalleFacturaDAO {

    public static final String NUMERO_PEDIDO = "NumeroPedido";
    public static final String NUMERO_FACTURA = "NumeroFactura";
    public static final String NUMERO_SECUENCIA = "NumeroSecuencia";
    public static final String ITEM_ID = "ItemID";
    public static final String CANTIDAD = "Cantidad";
    public static final String PRECIO = "Precio";
    public static final String I_DETALLE_FACTURA = "iDetalleFactura";
    public static final String DESCRIPCION = "Descripcion";
    public static final String PRECIO_DOLARES = "PrecioDolares";
    public static final String DESCUENTO_DISTRIBUIDOR = "DescuentoDistribuidor";
    public static final String DESCUENTO_PROMOCION = "DescuentoPromocion";
    public static final String DESCUENTO_ORDEN_COMPRA = "DescuentoOrdenCompra";
    public static final String CLIENTE_ID = "ClienteID";
    private static String NOMBRE_TABLE = "DetalleFactura";
    private Context context;

    public DetalleFacturaDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(DetalleFactura detaleFactura) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(NUMERO_PEDIDO, detaleFactura.NumeroPedido);
        contentValues.put(NUMERO_FACTURA, detaleFactura.NumeroFactura);
        contentValues.put(NUMERO_SECUENCIA, detaleFactura.NumeroSecuencia);
        contentValues.put(ITEM_ID, detaleFactura.ItemID);
        contentValues.put(CANTIDAD, detaleFactura.Cantidad);
        contentValues.put(PRECIO, detaleFactura.Precio);
        contentValues.put(DESCRIPCION, detaleFactura.Descripcion);
        contentValues.put(PRECIO_DOLARES, detaleFactura.PrecioDolares);
        contentValues.put(DESCUENTO_DISTRIBUIDOR, detaleFactura.DescuentoDistribuidor);
        contentValues.put(DESCUENTO_PROMOCION, detaleFactura.DescuentoPromocion);
        contentValues.put(DESCUENTO_ORDEN_COMPRA, detaleFactura.DescuentoOrdenCompra);
        contentValues.put(CLIENTE_ID, detaleFactura.ClienteID);
        return contentValues;
    }

    private static DetalleFactura getDetalleFactura(Cursor cursor) {
        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.iDetalleFactura = cursor.getInt(cursor.getColumnIndex(I_DETALLE_FACTURA));
        detalleFactura.NumeroFactura = cursor.getString(cursor.getColumnIndex(NUMERO_FACTURA));
        detalleFactura.NumeroPedido = cursor.getString(cursor.getColumnIndex(NUMERO_PEDIDO));
        detalleFactura.NumeroSecuencia = cursor.getString(cursor.getColumnIndex(NUMERO_SECUENCIA));
        detalleFactura.ItemID = cursor.getString(cursor.getColumnIndex(ITEM_ID));
        detalleFactura.Cantidad = cursor.getDouble(cursor.getColumnIndex(CANTIDAD));
        detalleFactura.Precio = cursor.getDouble(cursor.getColumnIndex(PRECIO));
        detalleFactura.Descripcion = cursor.getString(cursor.getColumnIndex(DESCRIPCION));
        detalleFactura.DescuentoDistribuidor = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_DISTRIBUIDOR));
        detalleFactura.DescuentoOrdenCompra = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_ORDEN_COMPRA));
        detalleFactura.DescuentoPromocion = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_PROMOCION));
        detalleFactura.PrecioDolares = cursor.getDouble(cursor.getColumnIndex(PRECIO_DOLARES));
        detalleFactura.ClienteID = cursor.getString(cursor.getColumnIndex(CLIENTE_ID));
        return detalleFactura;
    }

    public int registrarDetalleFactura(DetalleFactura detaleFactura) {
        int iDetalleFactura;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            iDetalleFactura = (int) db.getDataBase().insert(NOMBRE_TABLE, null, getContentValues(detaleFactura));
        } catch (Exception ex) {
            iDetalleFactura = -1;
        } finally {
            db.close();
        }

        return iDetalleFactura;
    }

    public ArrayList<DetalleFactura> listarDetalleFacturaxNroFactura(String numeroFactura) {
        ArrayList<DetalleFactura> detalleFacturas = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            detalleFacturas = new ArrayList<DetalleFactura>();
            db.openDataBase();
            String SQL = "SELECT * FROM " + NOMBRE_TABLE + " WHERE NumeroFactura=?  ORDER BY NumeroSecuencia asc";
            cursor = db.getDataBase().rawQuery(SQL, new String[]{numeroFactura});
            while (cursor.moveToNext()) {
                detalleFacturas.add(getDetalleFactura(cursor));
            }
        } catch (Exception ex) {
            detalleFacturas = null;
        } finally {
            db.close();
        }
        return detalleFacturas;
    }

    public boolean eliminarDetalleFactura() {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "1==1", null);
            estado = true;
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }


}
