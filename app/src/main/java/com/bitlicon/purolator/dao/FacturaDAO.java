package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.bitlicon.purolator.entities.Factura;
import java.util.ArrayList;

public class FacturaDAO {

    public static final String CLIENTE_ID = "ClienteID";
    public static final String TOTAL_IMPORTE = "TotalImporte";
    public static final String TOTAL_IMPUESTOS = "TotalImpuestos";
    public static final String TOTAL_DESCUENTO = "TotalDescuento";
    public static final String DESCUENTO_1 = "Descuento1";
    public static final String DESCUENTO_2 = "Descuento2";
    public static final String DESCUENTO_3 = "Descuento3";
    public static final String TOTAL_ORDEN = "TotalOrden";
    public static final String FECHA_FACTURA = "FechaFactura";
    public static final String TERMINO_VENTA = "TerminoVenta";
    public static final String DESCUENTO_ITEM = "DescuentoItem";
    public static final String NUMERO_FACTURA = "NumeroFactura";
    public static final String VENDEDOR_ID = "VendedorID";
    private static String NOMBRE_TABLE = "Factura";
    private Context context;

    public FacturaDAO(Context context) {
        this.context = context;
    }

    public ArrayList<Factura> listarTresUltimasFacturaxCliente(String clienteId, String vendedorID, int linea) {
        ArrayList<Factura> facturas = new ArrayList<Factura>();
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {

            db.openDataBase();
            String SQL = "SELECT * FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND VendedorID=? order by date(FechaFactura) desc LIMIT 3 ";
            Log.d("FacturaxCliente", SQL);
            cursor = db.getDataBase().rawQuery(SQL, new String[]{clienteId, vendedorID});

            while (cursor.moveToNext()) {
                Factura factura = getFactura(cursor);
                factura.Linea = linea;
                facturas.add(factura);
            }
        } catch (Exception ex) {
            Log.e("FacturaxCliente DAO", ex.getMessage(), ex);
            facturas = new ArrayList<Factura>();
            throw ex;
        } finally {
            db.close();
        }
        return facturas;
    }

    public ArrayList<Factura> listarFacturaxCliente(String clienteId, String vendedorID, int linea) {
        ArrayList<Factura> facturas = new ArrayList<Factura>();
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            String SQL = "SELECT * FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND VendedorID=? order by date(FechaFactura) desc";
            Log.d("FacturaxCliente", SQL);
            cursor = db.getDataBase().rawQuery(SQL, new String[]{clienteId, vendedorID});
            while (cursor.moveToNext()) {
                Factura factura = getFactura(cursor);
                factura.Linea = linea;
                facturas.add(factura);
            }
        } catch (Exception ex) {
            Log.e("FacturaxCliente DAO", ex.getMessage(), ex);
            facturas = new ArrayList<Factura>();
            throw ex;
        } finally {
            db.close();
        }
        return facturas;
    }

    public Factura buscarFactura(String numeroDocumento) {
        Factura factura = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {

            db.openDataBase();
            String SQL = "SELECT * FROM " + NOMBRE_TABLE + " WHERE NumeroFactura LIKE '%" + numeroDocumento + "%'";
            Log.d("buscarFactura", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);

            while (cursor.moveToNext()) {
                factura = getFactura(cursor);
            }
        } catch (Exception ex) {
            Log.e("buscarFactura DAO", ex.getMessage(), ex);
            factura = null;
            throw ex;
        } finally {
            db.close();
        }
        return factura;
    }

    public Factura getFactura(Cursor cursor) {
        Factura factura = new Factura();
        factura.IFactura = cursor.getInt(cursor.getColumnIndex("IFactura"));
        factura.NumeroFactura = cursor.getString(cursor.getColumnIndex(NUMERO_FACTURA));
        factura.VendedorID = cursor.getString(cursor.getColumnIndex(VENDEDOR_ID));
        factura.ClienteID = cursor.getString(cursor.getColumnIndex(CLIENTE_ID));
        factura.TotalImporte = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPORTE));
        factura.TotalImpuestos = cursor.getDouble(cursor.getColumnIndex(TOTAL_IMPUESTOS));
        factura.TotalDescuento = cursor.getDouble(cursor.getColumnIndex(TOTAL_DESCUENTO));
        factura.Descuento1 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_1));
        factura.Descuento2 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_2));
        factura.Descuento3 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_3));
        factura.TotalOrden = cursor.getDouble(cursor.getColumnIndex(TOTAL_ORDEN));
        factura.FechaFactura = cursor.getString(cursor.getColumnIndex(FECHA_FACTURA));
        factura.TerminoVenta = cursor.getString(cursor.getColumnIndex(TERMINO_VENTA));
        factura.DescuentoItem = cursor.getDouble(cursor.getColumnIndex(DESCUENTO_ITEM));
        return factura;
    }

    public int registrarFactura(Factura factura) {
        int ifactura;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ClienteID", factura.ClienteID);
            contentValues.put("NumeroFactura", factura.NumeroFactura);
            contentValues.put("TotalImporte", factura.TotalImporte);
            contentValues.put("TotalImpuestos", factura.TotalImpuestos);
            contentValues.put("VendedorID", factura.VendedorID);
            contentValues.put("FechaFactura", factura.FechaFactura);
            contentValues.put("Descuento1", factura.Descuento1);
            contentValues.put("Descuento2", factura.Descuento2);
            contentValues.put("Descuento3", factura.Descuento3);
            contentValues.put("DescuentoItem", factura.DescuentoItem);
            contentValues.put("TerminoVenta", factura.TerminoVenta);
            contentValues.put("TotalOrden", factura.TotalOrden);
            contentValues.put("TotalDescuento", factura.TotalDescuento);
            db.openDataBase();
            ifactura = (int) db.getDataBase().insert(NOMBRE_TABLE, null, contentValues);
        } catch (Exception ex) {
            Log.e("RegistrarFactura DAO", ex.getMessage(), ex);
            ifactura = -1;
        } finally {
            db.close();
        }
        return ifactura;
    }

    public boolean eliminarFacturas() {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "1==1", null);
            estado = true;
        } catch (Exception ex) {
            Log.e("EliminarFacturas DAO", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }

}
