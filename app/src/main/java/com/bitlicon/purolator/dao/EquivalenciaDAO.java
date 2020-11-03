package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Equivalencia;
import com.bitlicon.purolator.entities.Producto;

import java.util.ArrayList;

/**
 * Created by EduardoAndrés on 16/12/2015.
 */
public class EquivalenciaDAO
{
    public static final String CODIGOORIGINAL = "CodigoOriginal";
    public static final String MARCAORIGINAL = "MarcaOriginal";
    public static final String CODIGOPUROLATOR = "CodigoPurolator";
    public static final String PROCEDENCIA = "Procedencia";
    public static final String PRODUCTO_ID = "ProductoID";
    public static final String NOMBRE = "Nombre";
    public static final String PRECIOSOLES = "PrecioSoles";
    public static final String PRECIODOLARES = "PrecioDolares";
    public static final String STOCK = "Stock";
    public static final String FECHAREGISTRO = "FechaRegistro";
    public static final String ESTADO1 = "Estado1";
    public static final String TIPO = "Tipo";
    public static final String DIAMETROINTERIOR = "DiametroInterior";
    public static final String DIAMETROEXTERIOR = "DiametroExterior";
    public static final String APLICACIONES = "Aplicaciones";
    public static final String ALTURA = "Altura";
    public static final String ROSCA = "Rosca";
    public static final String ANCHO = "Ancho";
    public static final String LONGITUD = "Longitud";
    public static final String DIAMETROEXTERIORMAX = "DiametroExteriorMaximo";
    public static final String DIAMETROEXTERIORMIN = "DiametroExteriorMinimo";
    public static final String DIAMETROINTERIORMAX = "DiametroInteriorMaximo";
    public static final String DIAMETROINTERIORMIN = "DiametroInteriorMinimo";
    public static final String VALVULADERIVACION = "ValvulaDerivacion";
    public static final String VALVULAANTIRETORNO = "ValvulaAntiRetorno";
    public static final String CODIGOPRIMARIO = "CodigoPrimario";
    public static final String CLASE = "Clase";
    public static final String SUBCLASE = "SubClase";
    public static final String CATEGORIA = "Categoria";
    public static final String KIT1 = "Kit1";
    public static final String KIT2 = "Kit2";
    public static final String KIT3 = "Kit3";
    public static final String KIT4 = "Kit4";
    public static final String KIT5 = "Kit5";
    public static final String KIT6 = "Kit6";
    private static String NOMBRE_TABLE = "Equivalencia";
    public static final String CLASEWEB = "ClaseWeb";
    public static final String SUBCLASEWEB = "SubClaseWeb";
    public static final String LINEA = "Linea";
    public static final String FORMA = "Forma";
    private Context context;

    public EquivalenciaDAO(Context context)
    {
        this.context = context;
    }

    private static ContentValues getContentValues(Equivalencia equiv)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CODIGOORIGINAL, equiv.CodigoOriginal);
        contentValues.put(MARCAORIGINAL, equiv.MarcaOriginal);
        contentValues.put(CODIGOPUROLATOR, equiv.CodigoPurolator);
        contentValues.put(PROCEDENCIA, equiv.Procedencia);

        return contentValues;
    }

    private Equivalencia getEquivalencia(Cursor cursor)
    {
        Equivalencia equiv = new Equivalencia();
        equiv.CodigoOriginal = cursor.getString(cursor.getColumnIndex(CODIGOORIGINAL));
        equiv.MarcaOriginal = cursor.getString(cursor.getColumnIndex(MARCAORIGINAL));
        equiv.CodigoPurolator = cursor.getString(cursor.getColumnIndex(CODIGOPUROLATOR));
        equiv.Procedencia = cursor.getString(cursor.getColumnIndex(PROCEDENCIA));
        equiv.producto.ProductoID = cursor.getString(cursor.getColumnIndex(PRODUCTO_ID));
        equiv.producto.Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
        equiv.producto.PrecioSoles = cursor.getDouble(cursor.getColumnIndex(PRECIOSOLES));
        equiv.producto.PrecioDolares = cursor.getDouble(cursor.getColumnIndex(PRECIODOLARES));
        equiv.producto.Stock = cursor.getDouble(cursor.getColumnIndex(STOCK));
        equiv.producto.FechaRegistro = cursor.getString(cursor.getColumnIndex(FECHAREGISTRO));
        equiv.producto.Estado1 = cursor.getString(cursor.getColumnIndex(ESTADO1));
        equiv.producto.Tipo = cursor.getString(cursor.getColumnIndex(TIPO));
        equiv.producto.DiametroInterior = cursor.getString(cursor.getColumnIndex(DIAMETROINTERIOR));
        equiv.producto.DiametroExterior = cursor.getString(cursor.getColumnIndex(DIAMETROEXTERIOR));
        equiv.producto.Aplicaciones = cursor.getString(cursor.getColumnIndex(APLICACIONES));
        equiv.producto.Altura = cursor.getDouble(cursor.getColumnIndex(ALTURA));
        equiv.producto.Ancho = cursor.getDouble(cursor.getColumnIndex(ANCHO));
        equiv.producto.Longitud = cursor.getDouble(cursor.getColumnIndex(LONGITUD));
        equiv.producto.Rosca = cursor.getString(cursor.getColumnIndex(ROSCA));
        equiv.producto.DiametroExteriorMaximo = cursor.getDouble(cursor.getColumnIndex(DIAMETROEXTERIORMAX));
        equiv.producto.DiametroExteriorMinimo = cursor.getDouble(cursor.getColumnIndex(DIAMETROEXTERIORMIN));
        equiv.producto.DiametroInteriorMaximo = cursor.getDouble(cursor.getColumnIndex(DIAMETROINTERIORMAX));
        equiv.producto.DiametroInteriorMinimo = cursor.getDouble(cursor.getColumnIndex(DIAMETROINTERIORMIN));
        equiv.producto.ValvulaDerivacion = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VALVULADERIVACION)));
        equiv.producto.ValvulaAntiRetorno = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VALVULAANTIRETORNO)));
        equiv.producto.CodigoPrimario = cursor.getString(cursor.getColumnIndex(CODIGOPRIMARIO));
        equiv.producto.Clase = cursor.getString(cursor.getColumnIndex(CLASE));
        equiv.producto.SubClase = cursor.getString(cursor.getColumnIndex(SUBCLASE));
        equiv.producto.ClaseWeb = cursor.getString(cursor.getColumnIndex(CLASEWEB));
        equiv.producto.SubClaseWeb = cursor.getString(cursor.getColumnIndex(SUBCLASEWEB));

        equiv.producto.Categoria = cursor.getString(cursor.getColumnIndex(CATEGORIA));
        equiv.producto.Linea = cursor.getString(cursor.getColumnIndex(LINEA));
        equiv.producto.Forma = cursor.getString(cursor.getColumnIndex(FORMA));
        return equiv;
    }

    public  String obtenerNombreProducto(String ProductId) {
        String Nombre = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            String SQL = "Select Nombre from Producto where ProductoID=?";
            Log.d("ProductoDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, new String[]{ProductId});
            while (cursor.moveToNext()) {
                Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            Nombre = null;
        } finally {
            db.close();
        }
        return Nombre;
    }

    public boolean eliminarEquivalencias() {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "1==1", null);
            estado = true;
        } catch (Exception ex) {
            Log.e("EliminarEquivalenci DAO", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }

    public ArrayList<Equivalencia> buscar(String Codigo)
    {
        ArrayList<Equivalencia> equivalencias = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            equivalencias = new ArrayList<Equivalencia>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT EQUIVALENCIA.*, PRODUCTO.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" INNER JOIN PRODUCTO ON (EQUIVALENCIA.CodigoPurolator = PRODUCTO.Nombre)  ");
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo ");
            sb.append(" WHERE EQUIVALENCIA.CodigoOriginal LIKE '" + Codigo + "%' ");

            sb.append(" ORDER BY CodigoOriginal ASC; ");

            String SQL = sb.toString();
            Log.d("EquivalenciaDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);

            while (cursor.moveToNext())
            {
                equivalencias.add(getEquivalencia(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            equivalencias = null;
        }
        finally
        {
            db.close();
        }

        return equivalencias;
    }
}
