package com.bitlicon.purolator.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


import com.bitlicon.purolator.dao.ManagerDB;


/**
 * Created by dianewalls on 17/07/2015.
 */
public class ManagerContentProvider extends ContentProvider {

    /**
     * Variable con contiene el nombre del paquete donde se encuentra el Content Provider dentro del proyecto
     */
    private static final String AUTHORITY = "com.bitlicon.purolator.contentprovider";
    /**
     * Variable que representa a la tabla Cliente dentro del Content Provider
     */
    private static final String PATH_CLIENTE = "ClienteContentProvider";
    /**
     * Objeto que contiene el Content URI donde se encuentran los datos de Cliente
     */
    public static final Uri CONTENT_URI_CLIENTE = Uri.parse("content://" + AUTHORITY + "/" + PATH_CLIENTE);
    private static final String PATH_MOVIMIENTO = "MovimientoContentProvider";
    public static final Uri CONTENT_URI_MOVIMIENTO = Uri.parse("content://" + AUTHORITY + "/" + PATH_MOVIMIENTO);
    private static final String PATH_FACTURA = "FacturaContentProvider";
    public static final Uri CONTENT_URI_FACTURA = Uri.parse("content://" + AUTHORITY + "/" + PATH_FACTURA);
    private static final String PATH_DETALLE_FACTURA = "DetalleFacturaContentProvider";
    public static final Uri CONTENT_URI_DETALLE_FACTURA = Uri.parse("content://" + AUTHORITY + "/" + PATH_DETALLE_FACTURA);
    private static final String PATH_PRODUCTO = "ProductoContentProvider";
    public static final Uri CONTENT_URI_PRODUCTO = Uri.parse("content://" + AUTHORITY + "/" + PATH_PRODUCTO);
    private static final String PATH_CLASE = "ClaseContentProvider";
    public static final Uri CONTENT_URI_CLASE = Uri.parse("content://" + AUTHORITY + "/" + PATH_CLASE);
    private static final String PATH_SUBCLASE = "SubClaseContentProvider";
    public static final Uri CONTENT_URI_SUBCLASE = Uri.parse("content://" + AUTHORITY + "/" + PATH_SUBCLASE);
    private static final String PATH_EQUIVALENCIA = "EquivalenciaContentProvider";
    public static final Uri CONTENT_URI_EQUIVALENCIA = Uri.parse("content://" + AUTHORITY + "/" + PATH_EQUIVALENCIA);

    private static final String PATH_DESCUENTO = "DescuentoContentProvider";
    public static final Uri CONTENT_URI_DESCUENTO = Uri.parse("content://" + AUTHORITY + "/" + PATH_DESCUENTO);

    private static final String PATH_PEDIDO = "PedidoContentProvider";
    public static final Uri CONTENT_URI_PEDIDO = Uri.parse("content://" + AUTHORITY + "/" + PATH_PEDIDO);

    private static final String PATH_DETALLE_PEDIDO = "DetallePedidoContentProvider";
    public static final Uri CONTENT_URI_DETALLE_PEDIDO = Uri.parse("content://" + AUTHORITY + "/" + PATH_DETALLE_PEDIDO);

    private static final String PATH_LETRA_PEDIDO = "LetraPedidoContentProvider";
    public static final Uri CONTENT_URI_LETRA_PEDIDO = Uri.parse("content://" + AUTHORITY + "/" + PATH_LETRA_PEDIDO);

    /**
     * Variables que contienes los codigos de busqueda de cada entidad dentro del Content Provider
     */
    private static final int CLIENTE = 1;
    private static final int MOVIMIENTO = 2;
    private static final int FACTURA = 3;
    private static final int DETALLE_FACTURA = 4;
    private static final int PRODUCTO = 5;
    private static final int CLASE = 6;
    private static final int SUBCLASE = 7;
    private static final int EQUIVALENCIA = 8;
    private static final int DESCUENTO = 9;
    private static final int PEDIDO = 10;
    private static final int DETALLE_PEDIDO = 11;
    private static final int LETRA_PEDIDO = 12;
    /**
     * Objeto que interpreta las URI que representan a cada entidad de la base de datos
     */
    private static UriMatcher uriMatcher;

    /**
     *  Estructura donde se agregar las URI para que sean interpretadas por el Content Provider
     */
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH_CLIENTE, CLIENTE);
        uriMatcher.addURI(AUTHORITY, PATH_MOVIMIENTO, MOVIMIENTO);
        uriMatcher.addURI(AUTHORITY, PATH_FACTURA, FACTURA);
        uriMatcher.addURI(AUTHORITY, PATH_DETALLE_FACTURA, DETALLE_FACTURA);
        uriMatcher.addURI(AUTHORITY, PATH_PRODUCTO, PRODUCTO);
        uriMatcher.addURI(AUTHORITY, PATH_CLASE, CLASE);
        uriMatcher.addURI(AUTHORITY, PATH_SUBCLASE, SUBCLASE);
        uriMatcher.addURI(AUTHORITY, PATH_EQUIVALENCIA, EQUIVALENCIA);
        uriMatcher.addURI(AUTHORITY, PATH_DESCUENTO, DESCUENTO);
        uriMatcher.addURI(AUTHORITY, PATH_PEDIDO, PEDIDO);
        uriMatcher.addURI(AUTHORITY, PATH_DETALLE_PEDIDO, DETALLE_PEDIDO);
        uriMatcher.addURI(AUTHORITY, PATH_LETRA_PEDIDO, LETRA_PEDIDO);
    }

    protected Context mContext;
    /**
     * Objeto que contiene el controlador de la base de datos
     */
    private ManagerDB database;

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getType(Uri arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onCreate() {
        database = ManagerDB.getInstance(getContext());
        return false;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numInserted = 0;
        String table = null;
        SQLiteDatabase db = database.getDb();
        switch (uriMatcher.match(uri)) {
            case CLIENTE:
                table = "Cliente";
                break;
            case MOVIMIENTO:
                table = "Movimiento";
                break;
            case FACTURA:
                table = "Factura";
                break;
            case DETALLE_FACTURA:
                table = "DetalleFactura";
                break;
            case PRODUCTO:
                table = "Producto";
                break;
            case CLASE:
                table = "Clase";
                break;
            case SUBCLASE:
                table = "SubClase";
                break;
            case EQUIVALENCIA:
                table = "Equivalencia";
                break;
            case DESCUENTO:
                table = "Descuento";
                break;
            case PEDIDO:
                table = "Pedido";
                break;
            case DETALLE_PEDIDO:
                table = "DetallePedido";
                break;
            case LETRA_PEDIDO:
                table = "LetraPedido";
                break;
        }

        db.beginTransaction();

        try {
            for (ContentValues cv : values) {
                long newID = db.insertOrThrow(table, "", cv);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }
            db.yieldIfContendedSafely();
            db.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } catch (SQLException e) {
            Log.d("Manage", e.getMessage());
        } finally {
            db.endTransaction();
            //db.close();
        }

        return numInserted;
    }

}
