package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Producto;
import com.bitlicon.purolator.fragment.ImageFragment;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.Calendar;

public class ProductoDAO {

    public static final String PRODUCTO_ID = "ProductoID";
    public static final String NOMBRE = "Nombre";
    public static final String DESCUENTO1 = "Descuento1";
    public static final String DESCUENTO2 = "Descuento2";
    public static final String DESCUENTO5 = "Descuento5";
    public static final String PRECIOSOLES = "PrecioSoles";
    public static final String PRECIODOLARES = "PrecioDolares";
    public static final String PRECIOOFICINA = "PrecioOficina";
    public static final String PRECIOLIMA = "PrecioLima";
    public static final String PRECIONORTE = "PrecioNorte";
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
    public static final String CLASE = "Clase";
    public static final String SUBCLASE = "SubClase";
    public static final String DIAMETROEXTERIORMAX = "DiametroExteriorMaximo";
    public static final String DIAMETROEXTERIORMIN = "DiametroExteriorMinimo";
    public static final String DIAMETROINTERIORMAX = "DiametroInteriorMaximo";
    public static final String DIAMETROINTERIORMIN = "DiametroInteriorMinimo";
    public static final String VALVULADERIVACION = "ValvulaDerivacion";
    public static final String VALVULAANTIRETORNO = "ValvulaAntiRetorno";
    public static final String CODIGOPRIMARIO = "CodigoPrimario";
    public static final String CATEGORIA = "Categoria";
    public static final String LINEA = "Linea";
    public static final String FORMA = "Forma";
    public static final String KIT1 = "Kit1";
    public static final String KIT2 = "Kit2";
    public static final String KIT3 = "Kit3";
    public static final String KIT4 = "Kit4";
    public static final String KIT5 = "Kit5";
    public static final String KIT6 = "Kit6";
    public static final String CLASEWEB = "ClaseWeb";
    public static final String SUBCLASEWEB = "SubClaseWeb";
    public static final String TOP = "Top";

    private static String NOMBRE_TABLE = "Producto";
    private Context context;

    public ProductoDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(Producto producto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCTO_ID, producto.ProductoID);
        contentValues.put(NOMBRE, producto.Nombre);
        contentValues.put(PRECIOSOLES, producto.PrecioSoles);
        contentValues.put(PRECIODOLARES, producto.PrecioDolares);
        contentValues.put(PRECIOOFICINA, producto.PrecioOficina);
        contentValues.put(PRECIOLIMA, producto.PrecioLima);
        contentValues.put(PRECIONORTE, producto.PrecioNorte);
        contentValues.put(TOP, producto.Top);
        contentValues.put(STOCK, producto.Stock);
        contentValues.put(FECHAREGISTRO, producto.FechaRegistro);
        contentValues.put(ESTADO1, producto.Estado1);
        contentValues.put(TIPO, producto.Tipo);
        contentValues.put(DIAMETROINTERIOR, producto.DiametroInterior);
        contentValues.put(DIAMETROEXTERIOR, producto.DiametroExterior);
        contentValues.put(APLICACIONES, producto.Aplicaciones);
        contentValues.put(ALTURA, producto.Altura);
        contentValues.put(ANCHO, producto.Ancho);
        contentValues.put(LONGITUD, producto.Longitud);
        contentValues.put(ROSCA, producto.Rosca);
        contentValues.put(DIAMETROEXTERIORMAX, producto.DiametroExteriorMaximo);
        contentValues.put(DIAMETROEXTERIORMIN, producto.DiametroExteriorMinimo);
        contentValues.put(DIAMETROINTERIORMAX, producto.DiametroInteriorMaximo);
        contentValues.put(DIAMETROINTERIORMIN, producto.DiametroInteriorMinimo);
        contentValues.put(VALVULADERIVACION, producto.ValvulaDerivacion);
        contentValues.put(VALVULAANTIRETORNO, producto.ValvulaAntiRetorno);
        contentValues.put(CODIGOPRIMARIO, producto.CodigoPrimario);
        contentValues.put(CATEGORIA, producto.Categoria);
        contentValues.put(FORMA, producto.Forma);
        contentValues.put(LINEA, producto.Linea);
        return contentValues;
    }

    private  Producto getProducto(Cursor cursor) {
        Producto producto = new Producto();
        producto.ProductoID = cursor.getString(cursor.getColumnIndex(PRODUCTO_ID));
        producto.Descuento1 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO1));
        producto.Descuento2 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO2));
        producto.Descuento5 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO5));

        producto.Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
        producto.PrecioSoles = cursor.getDouble(cursor.getColumnIndex(PRECIOSOLES));
        producto.PrecioDolares = cursor.getDouble(cursor.getColumnIndex(PRECIODOLARES));
        producto.PrecioOficina = cursor.getDouble(cursor.getColumnIndex(PRECIOOFICINA));
        producto.PrecioLima = cursor.getDouble(cursor.getColumnIndex(PRECIOLIMA));
        producto.PrecioNorte = cursor.getDouble(cursor.getColumnIndex(PRECIONORTE));
        producto.Top = cursor.getInt(cursor.getColumnIndex(TOP));
        producto.Stock = cursor.getDouble(cursor.getColumnIndex(STOCK));
        producto.FechaRegistro = cursor.getString(cursor.getColumnIndex(FECHAREGISTRO));
        producto.Estado1 = cursor.getString(cursor.getColumnIndex(ESTADO1));
        producto.Tipo = cursor.getString(cursor.getColumnIndex(TIPO));
        producto.DiametroInterior = cursor.getString(cursor.getColumnIndex(DIAMETROINTERIOR));
        producto.DiametroExterior = cursor.getString(cursor.getColumnIndex(DIAMETROEXTERIOR));
        producto.Aplicaciones = cursor.getString(cursor.getColumnIndex(APLICACIONES));
        producto.Altura = cursor.getDouble(cursor.getColumnIndex(ALTURA));
        producto.Ancho = cursor.getDouble(cursor.getColumnIndex(ANCHO));
        producto.Longitud = cursor.getDouble(cursor.getColumnIndex(LONGITUD));
        producto.Rosca = cursor.getString(cursor.getColumnIndex(ROSCA));
        producto.Clase = cursor.getString(cursor.getColumnIndex(CLASE));
        producto.SubClase = cursor.getString(cursor.getColumnIndex(SUBCLASE));
        producto.ClaseWeb = cursor.getString(cursor.getColumnIndex(CLASEWEB));
        producto.SubClaseWeb = cursor.getString(cursor.getColumnIndex(SUBCLASEWEB));
        producto.DiametroExteriorMaximo = cursor.getDouble(cursor.getColumnIndex(DIAMETROEXTERIORMAX));
        producto.DiametroExteriorMinimo = cursor.getDouble(cursor.getColumnIndex(DIAMETROEXTERIORMIN));
        producto.DiametroInteriorMaximo = cursor.getDouble(cursor.getColumnIndex(DIAMETROINTERIORMAX));
        producto.DiametroInteriorMinimo = cursor.getDouble(cursor.getColumnIndex(DIAMETROINTERIORMIN));
        producto.ValvulaDerivacion = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VALVULADERIVACION)));
        producto.ValvulaAntiRetorno = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VALVULAANTIRETORNO)));
        producto.CodigoPrimario = cursor.getString(cursor.getColumnIndex(CODIGOPRIMARIO));
        producto.Categoria = cursor.getString(cursor.getColumnIndex(CATEGORIA));
        producto.Forma = cursor.getString(cursor.getColumnIndex(FORMA));
        producto.Linea = cursor.getString(cursor.getColumnIndex(LINEA));
        producto.Kit1= cursor.getString(cursor.getColumnIndex(KIT1));
        if(producto.Kit1.trim().length()>0)
        {
            producto.Kit1 = obtenerNombreProducto(producto.Kit1);
        }
        producto.Kit2= cursor.getString(cursor.getColumnIndex(KIT2));
        if(producto.Kit2.trim().length()>0)
        {
            producto.Kit2 = obtenerNombreProducto(producto.Kit2);
        }
        producto.Kit3= cursor.getString(cursor.getColumnIndex(KIT3));
        if(producto.Kit3.trim().length()>0)
        {
            producto.Kit3 = obtenerNombreProducto(producto.Kit3);
        }
        producto.Kit4= cursor.getString(cursor.getColumnIndex(KIT4));
        if(producto.Kit4.trim().length()>0)
        {
            producto.Kit4 = obtenerNombreProducto(producto.Kit4);
        }
        producto.Kit5= cursor.getString(cursor.getColumnIndex(KIT5));
        if(producto.Kit5.trim().length()>0)
        {
            producto.Kit5 = obtenerNombreProducto(producto.Kit5);
        }
        producto.Kit6= cursor.getString(cursor.getColumnIndex(KIT6));
        if(producto.Kit6.trim().length()>0)
        {
            producto.Kit6 = obtenerNombreProducto(producto.Kit6);
        }

        return producto;
    }

    public String obtenerNombreProducto(String ProductId) {
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

    public Producto obtener(String ProductId) {
        Producto producto = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            String SQL = "Select * from Producto where ProductoID=? LIMIT 1";
            Log.d("ProductoDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, new String[]{ProductId});
            while (cursor.moveToNext()) {
                producto = new Producto();
                producto.ProductoID = cursor.getString(cursor.getColumnIndex(PRODUCTO_ID));
                producto.Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
                producto.PrecioSoles = cursor.getDouble(cursor.getColumnIndex(PRECIOSOLES));
                producto.PrecioDolares = cursor.getDouble(cursor.getColumnIndex(PRECIODOLARES));
                producto.PrecioOficina = cursor.getDouble(cursor.getColumnIndex(PRECIOOFICINA));
                producto.PrecioLima = cursor.getDouble(cursor.getColumnIndex(PRECIOLIMA));
                producto.PrecioNorte = cursor.getDouble(cursor.getColumnIndex(PRECIONORTE));

                producto.Descuento1 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO1));
                producto.Descuento2 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO2));
                producto.Descuento5 = cursor.getDouble(cursor.getColumnIndex(DESCUENTO5));

            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            producto = null;
        } finally {
            db.close();
        }
        return producto;
    }

    public boolean eliminarProductos()
    {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "1==1", null);
            estado = true;
        } catch (Exception ex) {
            Log.e("EliminarProductos DAO", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }

    public ArrayList<Producto> filtrarProductos(boolean nuevo, boolean oferta, boolean kits, boolean packs, String Orden, String Nombre)
    {
        ArrayList<Producto> productos = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            productos = new ArrayList<Producto>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT Producto.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo ");
            if(Nombre.length()<=0)
            {
                sb.append(" WHERE 1=1 ");
            }
            else
            {
                sb.append(" WHERE replace(Producto.Nombre,'-','') LIKE '%" + Nombre.replace("-","") + "%' ");
            }
            if(nuevo)
            {
                sb.append(" AND substr(FechaRegistro,5,4)=?");
            }
            if(oferta)
            {
                sb.append(" AND Estado1='3' ");
            }
            if(packs)
            {
                sb.append(" AND Tipo='PK' ");
            }
            if(kits)
            {
                sb.append(" AND substr(ProductoID,3,2)='06' ");

            }

            switch (Orden)
            {
                case "Nombre":
                    sb.append(" ORDER BY Nombre ASC");
                    break;
                case "Clase":
                    sb.append(" ORDER BY substr(ProductoID,3,2) ASC");
                    break;
                case "SubClase":
                    sb.append(" ORDER BY substr(ProductoID,5,2) ASC");
                    break;
                default:
                    sb.append(" ORDER BY Nombre ASC");
                    break;
            }

            String SQL = sb.toString();
            Log.d("ProductoDAO", SQL);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            if(nuevo)
            {
                cursor = db.getDataBase().rawQuery(SQL, new String[]{String.valueOf(year)});
            }
            else
            {
                cursor = db.getDataBase().rawQuery(SQL, null);
            }

            while (cursor.moveToNext())
            {
                productos.add(getProducto(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            productos = null;
        }
        finally
        {
            db.close();
        }

        return productos;
    }

    public ArrayList<Producto> buscarNombre(String Nombre, String Orden, boolean nuevo, boolean oferta, boolean kits, boolean packs)
    {
        ArrayList<Producto> productos = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            productos = new ArrayList<Producto>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT Producto.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo ");

            if(Nombre.length()<=0)
            {
                sb.append(" WHERE 1=1 ");
            }
            else
            {
                sb.append(" WHERE replace(Producto.Nombre,'-','') LIKE '%" + Nombre.replace("-","") + "%' ");
            }

            if(nuevo)
            {
                sb.append(" AND substr(FechaRegistro,5,4)=?");
            }
            if(oferta)
            {
                sb.append(" AND Estado1='3' ");
            }
            if(packs)
            {
                sb.append(" AND Tipo='PK' ");
            }
            if(kits)
            {
                sb.append(" AND substr(ProductoID,3,2)='06' ");

            }

            switch (Orden)
            {
                case "Nombre":
                    sb.append(" ORDER BY Nombre ASC");
                    break;
                case "Clase":
                    sb.append(" ORDER BY substr(ProductoID,3,2) ASC");
                    break;
                case "SubClase":
                    sb.append(" ORDER BY substr(ProductoID,5,2) ASC");
                    break;
                default:
                    sb.append(" ORDER BY Nombre ASC");
                    break;
            }

            String SQL = sb.toString();
            Log.d("ProductoDAO", SQL);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            if(nuevo)
            {
                cursor = db.getDataBase().rawQuery(SQL, new String[]{String.valueOf(year)});
            }
            else
            {
                cursor = db.getDataBase().rawQuery(SQL, null);
            }

            while (cursor.moveToNext())
            {
                productos.add(getProducto(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            productos = null;
        }
        finally
        {
            db.close();
        }

        return productos;
    }

    public ArrayList<Producto> listarTopProductos(int top)
    {
        ArrayList<Producto> productos = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            productos = new ArrayList<Producto>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT Producto.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo WHERE Producto.Top!=0 ");
            sb.append(" ORDER BY Producto.Top ASC LIMIT "+top);
            String SQL = sb.toString();
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext())
            {
                productos.add(getProducto(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            productos = null;
        }
        finally
        {
            db.close();
        }

        return productos;
    }

    public ArrayList<Producto> listarTopProductosPuro(int top)
    {
        ArrayList<Producto> productos = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            productos = new ArrayList<Producto>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT Producto.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo WHERE Producto.Top!=0 ");

            sb.append(" AND substr(Producto.ProductoID,1,2)!='09' ");

            sb.append(" ORDER BY Producto.Top ASC LIMIT "+top);
            String SQL = sb.toString();
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext())
            {
                productos.add(getProducto(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            productos = null;
        }
        finally
        {
            db.close();
        }

        return productos;
    }

    public ArrayList<Producto> listarTopProductosFi(int top)
    {
        ArrayList<Producto> productos = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            productos = new ArrayList<Producto>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT Producto.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo WHERE Producto.Top!=0 ");
            sb.append(" AND substr(Producto.ProductoID,1,2)!='00' ");
            sb.append(" ORDER BY Producto.Top ASC LIMIT "+top);
            String SQL = sb.toString();
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext())
            {
                productos.add(getProducto(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            productos = null;
        }
        finally
        {
            db.close();
        }

        return productos;
    }


    public ArrayList<Producto> listarProductos()
    {
        ArrayList<Producto> productos = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            productos = new ArrayList<Producto>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT Producto.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo ");
            sb.append(" ORDER BY Nombre ASC");

            String SQL = sb.toString();
            Log.d("ProductoDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);

            while (cursor.moveToNext()) {
                productos.add(getProducto(cursor));
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            productos = null;
        } finally {
            db.close();
        }
        return productos;
    }

    public ArrayList<Producto> buscarProductos(String CodigoClase, String CodigoSubClase, String Altura, String Rosca, String DiametroInterior, String DiametroExterior, String Orden)
    {
        ArrayList<Producto> productos = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try
        {
            productos = new ArrayList<Producto>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT Producto.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo ");
            sb.append(" WHERE 1=1 ");
            sb.append(" AND substr(ProductoID,3,2) = ? ");
            sb.append(" AND substr(ProductoID,5,2) = ? ");
            sb.append(" AND (Altura = ? OR ? = '') ");
            sb.append(" AND Rosca LIKE '%"+ Rosca +"%' ");
            sb.append(" AND DiametroInterior LIKE '%"+ DiametroInterior +"%' ");
            sb.append(" AND DiametroExterior LIKE '%"+ DiametroExterior +"%' ");

            switch (Orden)
            {
                case "Nombre":
                    sb.append(" ORDER BY Nombre ASC");
                    break;
                case "Clase":
                    sb.append(" ORDER BY substr(ProductoID,3,2) ASC");
                    break;
                case "SubClase":
                    sb.append(" ORDER BY substr(ProductoID,5,2) ASC");
                    break;
                default:
                    sb.append(" ORDER BY Nombre ASC");
                    break;
            }

            String SQL = sb.toString();
            Log.d("ProductoDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, new String[]{CodigoClase, CodigoSubClase, Altura, Altura});

            while (cursor.moveToNext())
            {
                productos.add(getProducto(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            productos = null;
        }
        finally
        {
            db.close();
        }

        return productos;
    }

    public ArrayList<Producto> buscarNombreFiltech(String Nombre, String Orden, boolean nuevo, boolean oferta, boolean kits, boolean packs)
    {
        ArrayList<Producto> productos = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            productos = new ArrayList<Producto>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT Producto.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo ");

            if(Nombre.length()<=0)
            {
                sb.append(" WHERE 1=1 ");
            }
            else
            {
                sb.append(" WHERE replace(Producto.Nombre,'-','') LIKE '%" + Nombre.replace("-","") + "%' ");
            }

            if(nuevo)
            {
                sb.append(" AND substr(FechaRegistro,5,4)=?");
            }
            if(oferta)
            {
                sb.append(" AND Estado1='3' ");
            }
            if(packs)
            {
                sb.append(" AND Tipo='PK' ");
            }
            if(kits)
            {
                sb.append(" AND substr(ProductoID,3,2)='06' ");

            }
            sb.append(" AND substr(ProductoID,1,2)!='00' ");

            switch (Orden)
            {
                case "Nombre":
                    sb.append(" ORDER BY Nombre ASC");
                    break;
                case "Clase":
                    sb.append(" ORDER BY substr(ProductoID,3,2) ASC");
                    break;
                case "SubClase":
                    sb.append(" ORDER BY substr(ProductoID,5,2) ASC");
                    break;
                default:
                    sb.append(" ORDER BY Nombre ASC");
                    break;
            }

            String SQL = sb.toString();
            Log.d("ProductoDAO", SQL);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            if(nuevo)
            {
                cursor = db.getDataBase().rawQuery(SQL, new String[]{String.valueOf(year)});
            }
            else
            {
                cursor = db.getDataBase().rawQuery(SQL, null);
            }

            while (cursor.moveToNext())
            {
                productos.add(getProducto(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            productos = null;
        }
        finally
        {
            db.close();
        }

        return productos;
    }

    public ArrayList<Producto> buscarNombrePurolator(String Nombre, String Orden, boolean nuevo, boolean oferta, boolean kits, boolean packs)
    {
        ArrayList<Producto> productos = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);

        try
        {
            productos = new ArrayList<Producto>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT Producto.*, Clase.Nombre AS Clase, SubClase.Nombre AS SubClase FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" LEFT JOIN Clase on substr(ProductoID,3,2) = Clase.Codigo ");
            sb.append(" LEFT JOIN SubClase on substr(ProductoID,5,2) = SubClase.Codigo ");

            if(Nombre.length()<=0)
            {
                sb.append(" WHERE 1=1 ");
            }
            else
            {
                sb.append(" WHERE replace(Producto.Nombre,'-','') LIKE '%" + Nombre.replace("-","") + "%' ");
            }

            if(nuevo)
            {
                sb.append(" AND substr(FechaRegistro,5,4)=?");
            }
            if(oferta)
            {
                sb.append(" AND Estado1='3' ");
            }
            if(packs)
            {
                sb.append(" AND Tipo='PK' ");
            }
            if(kits)
            {
                sb.append(" AND substr(ProductoID,3,2)='06' ");

            }
            sb.append(" AND substr(ProductoID,1,2)!='09' ");

            switch (Orden)
            {
                case "Nombre":
                    sb.append(" ORDER BY Nombre ASC");
                    break;
                case "Clase":
                    sb.append(" ORDER BY substr(ProductoID,3,2) ASC");
                    break;
                case "SubClase":
                    sb.append(" ORDER BY substr(ProductoID,5,2) ASC");
                    break;
                default:
                    sb.append(" ORDER BY Nombre ASC");
                    break;
            }

            String SQL = sb.toString();
            Log.d("ProductoDAO", SQL);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            if(nuevo)
            {
                cursor = db.getDataBase().rawQuery(SQL, new String[]{String.valueOf(year)});
            }
            else
            {
                cursor = db.getDataBase().rawQuery(SQL, null);
            }

            while (cursor.moveToNext())
            {
                productos.add(getProducto(cursor));
            }
        }
        catch (Exception ex)
        {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            productos = null;
        }
        finally
        {
            db.close();
        }

        return productos;
    }



}
