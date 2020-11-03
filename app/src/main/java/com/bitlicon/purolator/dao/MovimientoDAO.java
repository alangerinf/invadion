package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Movimiento;
import com.bitlicon.purolator.entities.Resumen;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.Calendar;

public class MovimientoDAO {

    public static final String VENDEDOR_ID = "VendedorID";
    public static final String MOVIMIENTO_ID = "MovimientoID";
    public static final String MONTO = "Monto";
    public static final String PAGADA = "Pagada";
    public static final String LETBAN = "Letban";
    public static final String AGENCIA = "Agencia";
    public static final String NOMBRE_TABLE = "Movimiento";
    public static final String IMOVIMIENTO = "iMovimiento";
    public static final String NUM_DOCUMENTO = "NumDocumento";
    public static final String TIPO_DOCUMENTO = "TipoDocumento";
    public static final String SALDO = "Saldo";
    public static final String CLIENTE_ID = "ClienteID";
    public static final String FECHA_VENCIMIENTO = "FechaVencimiento";
    public static final String FECHA_DOCUMENTO = "FechaDocumento";
    public static final String TOTAL_PUROLATOR = "TotalPurolator";
    public static final String TOTAL_FILTECH = "TotalFiltech";
    public static final String TOTAL = "Total";
    private Context context;


    public MovimientoDAO(Context context) {
        this.context = context;
    }

    private static Cliente getClienteResumen(Cursor cursor) {
        Cliente cliente = new Cliente();
        cliente.ClienteID = cursor.getString(cursor.getColumnIndex(CLIENTE_ID));
        cliente.Nombre = cursor.getString(cursor.getColumnIndex(ClienteDAO.NOMBRE));
        cliente.TotalPurolator = cursor.getDouble(cursor.getColumnIndex(TOTAL_PUROLATOR));
        cliente.TotalFiltech = cursor.getDouble(cursor.getColumnIndex(TOTAL_FILTECH));
        cliente.Total = cursor.getDouble(cursor.getColumnIndex(TOTAL));
        return cliente;
    }

    private ContentValues getContentValues(Movimiento movimiento) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NUM_DOCUMENTO, movimiento.NumDocumento);
        contentValues.put(SALDO, movimiento.Saldo);
        contentValues.put(FECHA_VENCIMIENTO, movimiento.FechaVencimiento);
        contentValues.put(TIPO_DOCUMENTO, movimiento.TipoDocumento);
        contentValues.put(CLIENTE_ID, movimiento.ClienteID);
        contentValues.put(VENDEDOR_ID, movimiento.VendedorID);
        contentValues.put(MOVIMIENTO_ID, movimiento.MovimientoID);
        contentValues.put(MONTO, movimiento.Monto);
        contentValues.put(PAGADA, movimiento.Pagada);
        contentValues.put(LETBAN, movimiento.Letban);
        contentValues.put(AGENCIA, movimiento.Agencia);
        contentValues.put(FECHA_DOCUMENTO, movimiento.FechaDocumento);
        return contentValues;
    }

    public int registrarMovimiento(Movimiento movimiento) {
        int iMovimiento;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            iMovimiento = (int) db.getDataBase().insert(NOMBRE_TABLE, null, getContentValues(movimiento));
        } catch (Exception ex) {
            Log.e("RegistrarFactura DAO", ex.getMessage(), ex);
            iMovimiento = -1;
        } finally {
            db.close();
        }
        return iMovimiento;
    }

    public Double deudaPorClienteID(String clienteID, int tipo) {
        Double deuda = Double.valueOf(0f);
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        Calendar c = Calendar.getInstance();
        String fechaHoy = Util.formatoFechaQuery(c.getTime());
        c.add(Calendar.DATE, -8);
        String fechaMenosOcho = Util.formatoFechaQuery(c.getTime());
        try {
            db.openDataBase();
            String sql = "";
            switch (tipo) {
                case Constantes.VENCIDAS:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND Saldo!=0 AND TipoDocumento='L' AND date(FechaVencimiento) <= date(?) AND date(FechaVencimiento) >= date(?)) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND Saldo!=0 AND TipoDocumento in ('B','D') ) AS Deuda";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID, fechaHoy, fechaMenosOcho,clienteID});
                    break;
                case Constantes.CORRIENTE:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND Saldo!=0  AND date(FechaVencimiento) > date(?) AND TipoDocumento not in('B','D')) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND Saldo!=0 AND TipoDocumento in ('B','D') ) AS Deuda";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID, fechaHoy,clienteID});
                    break;
                case Constantes.LETRAS:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND Saldo!=0 AND TipoDocumento='L' AND date(FechaVencimiento) > date(?)) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND Saldo!=0 AND TipoDocumento in ('B','D') ) AS Deuda";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID, fechaHoy,clienteID});
                    break;
                case Constantes.MOROSA:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=?  AND Saldo!=0  AND date(FechaVencimiento) < date(?) AND TipoDocumento not in('B','D')) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND Saldo!=0 AND TipoDocumento in ('B','D') ) AS Deuda";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID, fechaMenosOcho,clienteID});
                    break;
                case Constantes.DEUDA:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=?  AND Saldo!=0 AND TipoDocumento not in('B','D')) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND Saldo!=0 AND TipoDocumento in ('B','D') ) AS Deuda";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID,clienteID});
                    break;
            }
            while (cursor.moveToNext()) {
                deuda = cursor.getDouble(cursor.getColumnIndex("Deuda"));
            }
        } catch (Exception ex) {
            Log.e("MovimientoDAO", ex.getMessage());
        } finally {
            db.close();
        }
        return deuda;
    }

    public Double deudas(int tipo, int dias)
    {
        Double deuda = Double.valueOf(0f);
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        Calendar c = Calendar.getInstance();
        String fechaHoy = Util.formatoFechaQuery(c.getTime());
        c.add(Calendar.DATE, -dias);
        String fechaMenosOcho = Util.formatoFechaQuery(c.getTime());
        try
        {
            db.openDataBase();
            String sql = "";
            switch (tipo)
            {
                case Constantes.VENCIDAS:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE  Saldo!=0 AND TipoDocumento='L' AND date(FechaVencimiento) <= date(?) AND date(FechaVencimiento) >= date(?)) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE Saldo!=0 AND TipoDocumento in ('B','D') ) AS Deuda";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{ fechaHoy, fechaMenosOcho});
                    break;
                case Constantes.CORRIENTE:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE Saldo!=0  AND date(FechaVencimiento) > date(?) AND TipoDocumento not in('B','D')) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE Saldo!=0 AND TipoDocumento in ('B','D') ) AS Deuda";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{ fechaHoy});
                    break;
                case Constantes.LETRAS:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE Saldo!=0 AND TipoDocumento='L' AND date(FechaVencimiento) > date(?)) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE Saldo!=0 AND TipoDocumento in ('B','D') ) AS Deuda";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{fechaHoy});
                    break;
                case Constantes.MOROSA:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE Saldo!=0  AND date(FechaVencimiento) < date(?) AND TipoDocumento not in('B','D')) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE Saldo!=0 AND TipoDocumento in('B','D')) AS Deuda ";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{ fechaMenosOcho});
                    break;
                case Constantes.DEUDA:
                    sql = "SELECT (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE + " WHERE Saldo!=0 AND TipoDocumento not in('B','D') ) - ";
                    sql += " (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE + " WHERE Saldo!=0 AND TipoDocumento in('B','D')) AS Deuda";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{});
                    break;
            }
            while (cursor.moveToNext())
            {
                deuda = cursor.getDouble(cursor.getColumnIndex("Deuda"));
            }
        } catch (Exception ex) {
            Log.e("MovimientoDAO", ex.getMessage());
        } finally {
            db.close();
        }
        return deuda;
    }

    public ArrayList<Movimiento> listarMovimientos(String clienteID, int tipo, String documentos) {
        ArrayList<Movimiento> movimientos = new ArrayList<>();
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        Calendar c = Calendar.getInstance();
        String fechaHoy = Util.formatoFechaQuery(c.getTime());
        c.add(Calendar.DATE, -8);
        String fechaMenosOcho = Util.formatoFechaQuery(c.getTime());
        try {
            db.openDataBase();
            String sql = "";
            switch (tipo) {
                case Constantes.VENCIDAS:
                    sql = "SELECT * FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND Saldo!=0 AND TipoDocumento='L' AND date(FechaVencimiento) <= date(?) AND date(FechaVencimiento) >= date(?)";

                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID, fechaHoy, fechaMenosOcho});
                    break;
                case Constantes.CORRIENTE:
                    sql = "SELECT * FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND  Saldo!=0  AND date(FechaVencimiento) > date(?)";
                    if (documentos.length() > 0) {
                        sql = sql + " AND TipoDocumento in " + documentos;
                    }
                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID, fechaHoy});
                    break;
                case Constantes.LETRAS:
                    sql = "SELECT * FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND  Saldo!=0 AND TipoDocumento='L' AND date(FechaVencimiento) > date(?)";
                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID, fechaHoy});
                    break;
                case Constantes.MOROSA:
                    sql = "SELECT * FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND  Saldo!=0  AND date(FechaVencimiento) < date(?)";
                    if (documentos.length() > 0) {
                        sql = sql + " AND TipoDocumento in " + documentos;
                    }
                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID, fechaMenosOcho});
                    break;
                case Constantes.DEUDA:
                    sql = "SELECT * FROM " + NOMBRE_TABLE + " WHERE ClienteID=? AND  Saldo!=0";
                    if (documentos.length() > 0) {
                        sql = sql + " AND TipoDocumento in " + documentos;
                    }
                    cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID});
                    break;
            }
            Log.d("Detalle deuda", sql);
            while (cursor.moveToNext()) {
                movimientos.add(getMovimiento(cursor));
            }
        } catch (Exception ex) {
            Log.e("MovimientoDAO", ex.getMessage());
        } finally {
            db.close();
        }
        return movimientos;
    }

    public ArrayList<Movimiento> listarMovimientosDeudaMultiple(String clienteID, String tipoCadena, String documentos, String Linea) {
        ArrayList<Movimiento> movimientos = new ArrayList<>();
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        Calendar c = Calendar.getInstance();
        String fechaHoy = Util.formatoFechaQuery(c.getTime());
        c.add(Calendar.DATE, -8);
        String fechaMenosOcho = Util.formatoFechaQuery(c.getTime());
        try {
            db.openDataBase();
            String sql = "SELECT * FROM " + NOMBRE_TABLE + " WHERE ClienteID='"+clienteID+"' AND Saldo!=0 AND (" ;
            String[] arregloTipo = tipoCadena.split(",");
            int tamanioArregloTipo = arregloTipo.length;
            int tipo = 0;
            boolean entro = false;
            for(int i=0;i<tamanioArregloTipo;i++) {
                if(!arregloTipo[i].equals("")) {
                    tipo = Integer.parseInt(arregloTipo[i]);
                    switch (tipo) {
                        case Constantes.VENCIDAS:
                            sql += " (TipoDocumento = 'L' AND date (FechaVencimiento) <= date('" + fechaHoy + "') AND date (FechaVencimiento) >= date('" + fechaMenosOcho + "')) OR";
                            entro = true;
                            break;
                        case Constantes.CORRIENTE:
                            sql += " ( date(FechaVencimiento) > date('" + fechaHoy + "') ) OR";
                            entro = true;
                            break;
                        case Constantes.LETRAS:
                            sql += " (TipoDocumento='L' AND date(FechaVencimiento) > date('" + fechaHoy + "')) OR";
                            entro = true;
                            break;
                        case Constantes.MOROSA:
                            sql += " (date(FechaVencimiento) < date('" + fechaMenosOcho + "')) OR";
                            entro = true;
                            break;
                        case Constantes.DEUDA:
                            sql += " (1=1) OR";
                            entro = true;
                            break;
                    }
                }
            }

            if(entro)
            {
                sql = sql.substring(0,sql.length() - 2);
            }else
            {
                sql += "1=1" ;
            }
            sql += " ) ";
            if (documentos.length() > 0) {
                sql = sql + " AND TipoDocumento in " + documentos;
            }
            if(!Linea.equals("")) {
                sql += " AND VendedorID=" + Linea;
            }
            sql+= " UNION SELECT * FROM " + NOMBRE_TABLE + " WHERE ClienteID='"+clienteID+"' AND Saldo!=0 AND TipoDocumento in ('B','D') ";
            if(!Linea.equals("")) {
                sql += " AND VendedorID=" + Linea;
            }
            sql+= " ORDER BY FechaVencimiento desc";
            Log.d("Detalle deuda", sql);
            cursor = db.getDataBase().rawQuery(sql, new String[]{});
            while (cursor.moveToNext()) {
                movimientos.add(getMovimiento(cursor));
            }

        } catch (Exception ex) {
            Log.e("MovimientoDAO", ex.getMessage());
        } finally {
            db.close();
        }
        return movimientos;
    }

    private Movimiento getMovimiento(Cursor cursor) {
        Movimiento movimiento;
        movimiento = new Movimiento();
        movimiento.iMovimiento = cursor.getInt(cursor.getColumnIndex(IMOVIMIENTO));
        movimiento.NumDocumento = cursor.getString(cursor.getColumnIndex(NUM_DOCUMENTO));
        movimiento.TipoDocumento = cursor.getString(cursor.getColumnIndex(TIPO_DOCUMENTO));
        movimiento.FechaVencimiento = cursor.getString(cursor.getColumnIndex(FECHA_VENCIMIENTO));
        movimiento.Saldo = cursor.getDouble(cursor.getColumnIndex(SALDO));
        movimiento.ClienteID = cursor.getString(cursor.getColumnIndex(CLIENTE_ID));
        movimiento.Pagada = cursor.getString(cursor.getColumnIndex(PAGADA));
        movimiento.Monto = cursor.getDouble(cursor.getColumnIndex(MONTO));
        movimiento.MovimientoID = cursor.getString(cursor.getColumnIndex(MOVIMIENTO_ID));
        movimiento.VendedorID = cursor.getString(cursor.getColumnIndex(VENDEDOR_ID));
        movimiento.Letban = cursor.getString(cursor.getColumnIndex(LETBAN));
        movimiento.Agencia = cursor.getString(cursor.getColumnIndex(AGENCIA));
        movimiento.FechaDocumento = cursor.getString(cursor.getColumnIndex(FECHA_DOCUMENTO));
        return movimiento;
    }

    public ArrayList<Movimiento> listarMovimientosFactura(String clienteID, String vendedorID, String fechaInicio, String fechaFin) {
        ArrayList<Movimiento> movimientos = new ArrayList<>();
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);

        try {

            String sql = "SELECT  iMovimiento,NumDocumento,ClienteID, TipoDocumento, FechaDocumento, CASE WHEN TipoDocumento in ('D','B' ) THEN -Monto ELSE Monto END as Monto FROM Movimiento " +
                    "WHERE DATE(FechaDocumento)<=DATE('" + fechaFin + "') AND DATE(FechaDocumento)>=DATE('" + fechaInicio + "') " +
                    "AND TipoDocumento in ('F','V','D','B' ) AND ClienteID= ? AND VendedorID=?";

            Log.d("Detalle deuda", sql);
            db.openDataBase();
            cursor = db.getDataBase().rawQuery(sql, new String[]{clienteID, vendedorID});

            while (cursor.moveToNext()) {

                Movimiento movimiento;
                movimiento = new Movimiento();
                movimiento.iMovimiento = cursor.getInt(cursor.getColumnIndex(IMOVIMIENTO));
                movimiento.NumDocumento = cursor.getString(cursor.getColumnIndex(NUM_DOCUMENTO));
                movimiento.TipoDocumento = cursor.getString(cursor.getColumnIndex(TIPO_DOCUMENTO));
                movimiento.Monto = cursor.getDouble(cursor.getColumnIndex(MONTO));
                movimiento.FechaDocumento = cursor.getString(cursor.getColumnIndex(FECHA_DOCUMENTO));
                movimiento.ClienteID = cursor.getString(cursor.getColumnIndex(CLIENTE_ID));
                movimientos.add(movimiento);
            }
        } catch (Exception ex) {
            Log.e("MovimientoDAO", ex.getMessage());
        } finally {
            db.close();
        }
        return movimientos;
    }

    public ArrayList<Cliente> obtenerMovimientoResumen(String linea1, String linea2, String fechaInicio, String fechaFin) {
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();

        StringBuilder sb = new StringBuilder();
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        sb.append("SELECT Nombre,Movimiento.ClienteID, ");
        sb.append("SUM( CASE WHEN VendedorID='" + linea1 + "' THEN Saldo ELSE 0 END) as DeudaPurolator, ");
        sb.append("SUM( CASE WHEN VendedorID='" + linea2 + "' THEN Saldo ELSE 0 END) as DeudaFiltech, ");
        sb.append("SUM(  Saldo) as DeudaTotal ");
        sb.append("FROM Movimiento ");
        sb.append("JOIN Cliente ON Movimiento.ClienteID=Cliente.ClienteID ");
        sb.append("GROUP BY Movimiento.ClienteID ");

        try {
            db.openDataBase();
            cursor = db.getDataBase().rawQuery(sb.toString(), null);
            while (cursor.moveToNext()) {
                Cliente cliente = new Cliente();
                cliente.Nombre = cursor.getString(cursor.getColumnIndex(ClienteDAO.NOMBRE));
                cliente.ClienteID = cursor.getString(cursor.getColumnIndex(ClienteDAO.CLIENTE_ID));
                cliente.DeudaPurolator = cursor.getDouble(cursor.getColumnIndex("DeudaPurolator"));
                cliente.DeudaFiltech = cursor.getDouble(cursor.getColumnIndex("DeudaFiltech"));
                cliente.DeudaTotal = cursor.getDouble(cursor.getColumnIndex("DeudaTotal"));
                clientes.add(cliente);
            }
        } catch (Exception ex) {
            Log.e("MovimientoDAO", ex.getMessage());
        } finally {
            db.close();
        }

        return clientes;
    }

    public boolean eliminarMovimientos() {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "1==1", null);
            estado = true;
        } catch (Exception ex) {
            Log.e("EliminarMovimientos DAO", ex.getMessage(), ex);

        } finally {
            db.close();
        }
        return estado;
    }

    public Resumen obtenerResumen(String linea1, String linea2, String fechaInicio, String fechaFin) {
        ManagerDB db = ManagerDB.getInstance(context);
        Cursor cursor = null;

        Resumen resumen = null;
        try {
            db.openDataBase();

            StringBuilder sb = new StringBuilder();

            sb.append("SELECT ");
            sb.append("SUM(CASE ");
            sb.append("WHEN VendedorID=? AND (TipoDocumento = 'F' OR TipoDocumento = 'V' )  THEN Monto ");
            sb.append("WHEN VendedorID=? AND (TipoDocumento = 'B' OR TipoDocumento = 'D' )  THEN -Monto ");
            sb.append("ELSE 0 END) as TotalPurolator, ");
            sb.append("SUM(CASE ");
            sb.append("WHEN VendedorID=? AND (TipoDocumento = 'F' OR TipoDocumento = 'V' )  THEN Monto ");
            sb.append("WHEN VendedorID=? AND (TipoDocumento = 'B' OR TipoDocumento = 'D' )  THEN -Monto ");
            sb.append("ELSE 0 END) as TotalFiltech, ");
            sb.append("SUM(CASE ");
            sb.append("WHEN (TipoDocumento = 'F' OR TipoDocumento = 'V' )  THEN Monto ");
            sb.append("WHEN (TipoDocumento = 'B' OR TipoDocumento = 'D' )  THEN -Monto ");
            sb.append("ELSE 0 END) as Total ");
            sb.append("FROM Movimiento ");
            sb.append("WHERE DATE(FechaDocumento)<=DATE('" + fechaFin + "') AND DATE(FechaDocumento)>=DATE('" + fechaInicio + "') ");
            sb.append("AND ( TipoDocumento = 'F' OR TipoDocumento = 'V' OR TipoDocumento = 'B' OR TipoDocumento = 'D' ) ");
            sb.append("ORDER BY FechaDocumento");
            String SQL = sb.toString();
            Log.d("Resumen", SQL);
            cursor = db.getDataBase().rawQuery(SQL, new String[]{linea1,linea1, linea2,linea2});
            while (cursor.moveToNext()) {
                resumen = new Resumen();
                resumen.setTotal(cursor.getDouble(cursor.getColumnIndex("Total")));
                resumen.setTotalPurolator(cursor.getDouble(cursor.getColumnIndex("TotalPurolator")));
                resumen.setTotalFiltech(cursor.getDouble(cursor.getColumnIndex("TotalFiltech")));
            }
        } catch (Exception e) {
            resumen = null;
        } finally {
            db.close();
        }
        return resumen;
    }

    public long cantidadDePedidosFacturados(String linea, String fechaInicio, String fechaFin) {
        ManagerDB db = ManagerDB.getInstance(context);
        Cursor cursor = null;

        long cantidad = 0;
        try {
            db.openDataBase();

            StringBuilder sb = new StringBuilder();

            sb.append("SELECT COUNT(1) as Cantidad ");
            sb.append("FROM Movimiento ");
            sb.append("WHERE DATE(FechaDocumento)<=DATE('" + fechaFin + "') AND DATE(FechaDocumento)>=DATE('" + fechaInicio + "') ");
            sb.append("AND ( TipoDocumento = 'F' OR TipoDocumento = 'V' ) ");
            sb.append("AND VendedorID=?");
            String SQL = sb.toString();
            Log.d("Resumen", SQL);
            cursor = db.getDataBase().rawQuery(SQL, new String[]{linea});
            while (cursor.moveToNext()) {
                cantidad = cursor.getLong(cursor.getColumnIndex("Cantidad"));

            }
        } catch (Exception e) {
            cantidad = 0;
        } finally {
            db.close();
        }
        return cantidad;
    }

    public ArrayList<Cliente> listarResumenCliente(String linea1, String linea2, String fechaInicio, String fechaFin) {
        ArrayList<Cliente> clientes = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            clientes = new ArrayList<Cliente>();

            StringBuilder sb = new StringBuilder();

            sb.append("SELECT Cliente.ClienteID, Nombre, ");
            sb.append("SUM(CASE ");
            sb.append("WHEN VendedorID=? AND (TipoDocumento = 'F' OR TipoDocumento = 'V' )  THEN Monto ");
            sb.append("WHEN VendedorID=? AND (TipoDocumento = 'B' OR TipoDocumento = 'D' )  THEN -Monto ");
            sb.append("ELSE 0 END) as TotalPurolator, ");
            sb.append("SUM(CASE ");
            sb.append("WHEN VendedorID=? AND (TipoDocumento = 'F' OR TipoDocumento = 'V' )  THEN Monto ");
            sb.append("WHEN VendedorID=? AND (TipoDocumento = 'B' OR TipoDocumento = 'D' )  THEN -Monto ");
            sb.append("ELSE 0 END) as TotalFiltech, ");
            sb.append("SUM(CASE ");
            sb.append("WHEN (TipoDocumento = 'F' OR TipoDocumento = 'V' )  THEN Monto ");
            sb.append("WHEN (TipoDocumento = 'B' OR TipoDocumento = 'D' )  THEN -Monto ");
            sb.append("ELSE 0 END) as Total ");
            sb.append("FROM Movimiento ");
            sb.append("INNER JOIN Cliente ON Cliente.ClienteID= Movimiento.ClienteID ");
            sb.append("WHERE DATE(FechaDocumento)<=DATE('" + fechaFin + "') AND DATE(FechaDocumento)>=DATE('" + fechaInicio + "') ");
            sb.append("AND ( TipoDocumento = 'F' OR TipoDocumento = 'V'  OR  TipoDocumento = 'B' OR TipoDocumento = 'D' ) ");
            sb.append("GROUP BY Movimiento.ClienteID ");
            sb.append("ORDER BY Nombre");

            String SQL = sb.toString();
            Log.d("ClienteDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, new String[]{linea1, linea1, linea2, linea2});
            while (cursor.moveToNext()) {
                clientes.add(getClienteResumen(cursor));
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            clientes = null;
        } finally {
            db.close();
        }
        return clientes;
    }
}
