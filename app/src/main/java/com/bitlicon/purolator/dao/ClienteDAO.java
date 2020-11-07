package com.bitlicon.purolator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.Calendar;

public class ClienteDAO {

    public static final String I_CLIENTE = "iCliente";
    public static final String CLIENTE_ID = "ClienteID";
    public static final String NOMBRE = "Nombre";
    public static final String DIRECCION = "Direccion";
    public static final String RUTA = "Ruta";
    public static final String TELEFONO = "Telefono";
    public static final String DNI = "DNI";
    public static final String ENCARGADO = "Encargado";
    public static final String RUC = "RUC";
    public static final String PUROLATOR_ID = "PurolatorID";
    public static final String FILTECH_ID = "FiltechID";
    public static final String MONTO_LINEA_CREDITO = "MontoLineaCredito";
    public static final String EMAIL = "Email";
    public static final String FECHA_CREACION = "FechaCreacion";
    public static final String NUEVO = "Nuevo";
    public static final String DESPACHO = "Despacho";
    public static final String GIRO = "Giro";
    public static final String REPRESENTANTE_LEGAL = "RepresentanteLegal";
    private static final String NOMBRE_TABLE = "Cliente";
    public static final String NOMBRE_TABLE_MOVIMIENTO = "Movimiento";
    private Context context;

    public ClienteDAO(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(Cliente cliente) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLIENTE_ID, cliente.ClienteID);
        contentValues.put(PUROLATOR_ID, cliente.PurolatorID);
        contentValues.put(FILTECH_ID, cliente.FiltechID);
        contentValues.put(NOMBRE, cliente.Nombre);
        contentValues.put(DIRECCION, cliente.Direccion);
        contentValues.put(DESPACHO, cliente.Despacho);
        contentValues.put(GIRO, cliente.Giro);
        contentValues.put(REPRESENTANTE_LEGAL, cliente.RepresentanteLegal);
        contentValues.put(RUTA, cliente.Ruta);
        contentValues.put(TELEFONO, cliente.Telefono);
        contentValues.put(DNI, cliente.DNI);
        contentValues.put(ENCARGADO, cliente.Encargado);
        contentValues.put(RUC, cliente.RUC);
        contentValues.put(MONTO_LINEA_CREDITO, cliente.MontoLineaCredito);
        contentValues.put(EMAIL, cliente.Email);
        contentValues.put(FECHA_CREACION, cliente.FechaCreacion);
        contentValues.put(NUEVO, cliente.Nuevo);
        return contentValues;
    }

    private static Cliente getCliente(Cursor cursor) {
        Cliente cliente = new Cliente();
        cliente.iCliente = cursor.getInt(cursor.getColumnIndex(I_CLIENTE));
        cliente.ClienteID = cursor.getString(cursor.getColumnIndex(CLIENTE_ID));
        cliente.Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
        cliente.Direccion = cursor.getString(cursor.getColumnIndex(DIRECCION));
        cliente.Despacho = cursor.getString(cursor.getColumnIndex(DESPACHO));
        cliente.Giro = cursor.getString(cursor.getColumnIndex(GIRO));
        cliente.RepresentanteLegal = cursor.getString(cursor.getColumnIndex(REPRESENTANTE_LEGAL));
        cliente.Ruta = cursor.getString(cursor.getColumnIndex(RUTA));
        cliente.Telefono = cursor.getString(cursor.getColumnIndex(TELEFONO));
        cliente.DNI = cursor.getString(cursor.getColumnIndex(DNI));
        cliente.Encargado = cursor.getString(cursor.getColumnIndex(ENCARGADO));
        cliente.RUC = cursor.getString(cursor.getColumnIndex(RUC));
        cliente.PurolatorID = cursor.getString(cursor.getColumnIndex(PUROLATOR_ID));
        cliente.FiltechID = cursor.getString(cursor.getColumnIndex(FILTECH_ID));
        cliente.MontoLineaCredito = cursor.getDouble(cursor.getColumnIndex(MONTO_LINEA_CREDITO));
        cliente.Email = cursor.getString(cursor.getColumnIndex(EMAIL));
        cliente.FechaCreacion = cursor.getString(cursor.getColumnIndex(FECHA_CREACION));
        cliente.Nuevo = cursor.getInt(cursor.getColumnIndex(NUEVO)) == 1;
        return cliente;
    }


    public long registrarCliente(Cliente cliente) {
        long estado = 0;
        Log.d("Cliente: ", cliente.toString());
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            estado = db.getDataBase().insert(NOMBRE_TABLE, null, getContentValues(cliente));
            Log.d(this.getClass().getName(), "Registro con exito");
        } catch (Exception ex) {
            Log.e("RegistrarCliente DAO", ex.getMessage(), ex);
            estado = -1;
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean eliminarClientes() {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "1==1", null);
            estado = true;
        } catch (Exception ex) {
            Log.e("EliminarClientes DAO", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }

    public boolean eliminarCliente(long iCliente) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().delete(NOMBRE_TABLE, "iCliente=?", new String[]{String.valueOf(iCliente)});
            estado = true;
        } catch (Exception ex) {
            Log.e("EliminarClientes DAO", ex.getMessage(), ex);
        } finally {
            db.close();
        }
        return estado;
    }

    public ArrayList<Cliente> buscarClientesAvanzada(String valor, String campo, String campoOrden, int dia, boolean nuevo, boolean par) {
        ArrayList<Cliente> clientes = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            clientes = new ArrayList<Cliente>();
            db.openDataBase();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(NOMBRE_TABLE);
            sb.append(" WHERE ");
            sb.append(campo);
            sb.append(" like '%");
            sb.append(valor);
            sb.append("%' ");
            if (dia > 0) {
                Log.i("semana", "es " + par);
                if (par) {
                    sb.append(" AND");
                    sb.append(" ( substr(Ruta,1,1) = '0' or substr(Ruta,1,1) = '2' ) ");
                } else {
                    sb.append(" AND");
                    sb.append(" ( substr(Ruta,1,1) = '0' or substr(Ruta,1,1) = '1' ) ");
                }
                sb.append(" AND");
                sb.append(" substr(Ruta,");
                sb.append(dia + Constantes.INDICE_RUTA);
                sb.append(",1) = '1'");
            }
            if (nuevo) {
                sb.append(" AND");
                sb.append(" Nuevo='1' ");
            } else {
                sb.append(" AND");
                sb.append(" ClienteID IS NOT NULL ");
            }
            sb.append(" ORDER by ");
            sb.append(campoOrden);
            sb.append(" ASC");

            String SQL = sb.toString();
            Log.d("ClienteDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);

            while (cursor.moveToNext()) {
                clientes.add(getCliente(cursor));
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            clientes = null;
        } finally {
            db.close();
        }
        return clientes;
    }

    public ArrayList<Cliente> buscarClientesAvanzadaxDeuda(String valor, String campo, String campoOrden, int dia, boolean nuevo, boolean par,int tipo) {
        ArrayList<Cliente> clientes = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            clientes = new ArrayList<Cliente>();
            db.openDataBase();
            String sql = "SELECT C.iCliente,C.MontoLineaCredito,C.ClienteID,C.Telefono,C.Email,C.Nombre, ";
            Calendar c = Calendar.getInstance();
            String fechaHoy = Util.formatoFechaQuery(c.getTime());
            c.add(Calendar.DATE, -8);
            String fechaMenosOcho = Util.formatoFechaQuery(c.getTime());

            switch (tipo) {
                case Constantes.VENCIDAS:
                    sql += "( (SELECT Sum(Saldo) FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID AND Saldo!=0 AND TipoDocumento='L' AND date(FechaVencimiento) <= date('"+fechaHoy+"') AND date(FechaVencimiento) >= date('"+fechaMenosOcho+"')) ";
                    sql += " - (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE Saldo!=0  AND ClienteID=C.ClienteID AND TipoDocumento in ('B','D')) ) as Deuda";
                    break;
                case Constantes.CORRIENTE:
                    sql += " ( (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID AND Saldo!=0  AND TipoDocumento not in('B','D') AND date(FechaVencimiento) > date('"+fechaHoy+"')) ";
                    sql += " - (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID AND Saldo!=0  AND TipoDocumento in ('B','D')) ) as Deuda";
                    break;
                case Constantes.LETRAS:
                    sql += " ( (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID AND Saldo!=0 AND TipoDocumento='L' AND date(FechaVencimiento) > date('"+fechaHoy+"')) ";
                    sql += " - (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID AND Saldo!=0  AND TipoDocumento in ('B','D')) ) as Deuda";
                    break;
                case Constantes.MOROSA:
                    sql += " ( (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID  AND Saldo!=0 AND TipoDocumento not in('B','D') AND date(FechaVencimiento) < date('"+fechaMenosOcho+"')) ";
                    sql += " - (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID AND Saldo!=0  AND TipoDocumento in('B','D')) ) as Deuda";
                    break;
                case Constantes.DEUDA:
                    sql += " ( (SELECT Sum(Saldo) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID  AND Saldo!=0 AND TipoDocumento not in('B','D')) - ";
                    sql += "   (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID AND Saldo!=0 AND TipoDocumento in('B','D')) )  as Deuda";
                    break;
            }
            sql += " FROM ";
            StringBuilder sb = new StringBuilder();
            sb.append(sql);
            sb.append(NOMBRE_TABLE);
            sb.append(" AS C WHERE Deuda>0 AND ");
            sb.append(campo);
            sb.append(" like '%");
            sb.append(valor);
            sb.append("%' ");
            if (dia > 0) {
                if (par) {
                    sb.append(" AND");
                    sb.append(" ( substr(C.Ruta,1,1) = '0' or substr(C.Ruta,1,1) = '2' ) ");
                } else {
                    sb.append(" AND");
                    sb.append(" ( substr(C.Ruta,1,1) = '0' or substr(C.Ruta,1,1) = '1' ) ");
                }
                sb.append(" AND");
                sb.append(" substr(C.Ruta,");
                sb.append(dia + Constantes.INDICE_RUTA);
                sb.append(",1) = '1'");
            }

            sb.append(" ORDER by C.");
            sb.append(campoOrden);
            sb.append(" ASC");

            String SQL = sb.toString();
            Log.d("ClienteDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);
            Cliente cliente;
            double DeudaTotal = 0;
            while (cursor.moveToNext()) {
                cliente = new Cliente();
                cliente.iCliente = cursor.getInt(cursor.getColumnIndex(I_CLIENTE));
                cliente.ClienteID = cursor.getString(cursor.getColumnIndex(CLIENTE_ID));
                cliente.Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
                cliente.Deuda = cursor.getDouble(cursor.getColumnIndex("Deuda"));
                cliente.Telefono = cursor.getString(cursor.getColumnIndex(TELEFONO));
                cliente.Email = cursor.getString(cursor.getColumnIndex(EMAIL));
                cliente.MontoLineaCredito = cursor.getDouble(cursor.getColumnIndex(MONTO_LINEA_CREDITO));
                DeudaTotal += cliente.Deuda;
                cliente.DeudaTotal = DeudaTotal;
                clientes.add(cliente);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            clientes = null;
        } finally {
            db.close();
        }
        return clientes;
    }

    public ArrayList<Cliente> buscarClientesAvanzadaxDeudaMultiple(String valor, String campo, String campoOrden, int dia, boolean nuevo, boolean par,String tipoCadena, String documentos, boolean checkabonoValor) {
        ArrayList<Cliente> clientes = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        boolean checkTipoDocumentos = false;
        try {
            clientes = new ArrayList<Cliente>();
            db.openDataBase();
            String sql = "SELECT C.iCliente,C.MontoLineaCredito,C.ClienteID,C.Telefono,C.Email,C.Nombre, ";
            Calendar c = Calendar.getInstance();
            String fechaHoy = Util.formatoFechaQuery(c.getTime());
            c.add(Calendar.DATE, -8);
            String fechaMenosOcho = Util.formatoFechaQuery(c.getTime());

            String[] arregloTipo = tipoCadena.split(",");
            int tamanioArregloTipo = arregloTipo.length;
            int tipo = 0;
            boolean entro = false;
            boolean entraResta = true;
            if(checkabonoValor && documentos.length() == 0)
            {
                sql+= " - ( ";
            }else {
                sql += " ( ( SELECT Sum(Saldo) FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID AND Saldo!=0 AND TipoDocumento not in('B','D') AND ( ";
                for (int i = 0; i < tamanioArregloTipo; i++) {
                    if (!arregloTipo[i].equals("")) {
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
                if (entro) {
                    sql = sql.substring(0, sql.length() - 2);
                } else {
                    sql += "1=1";
                }
                sql += ")";

                if (documentos.length() > 0) {
                    sql = sql + " AND TipoDocumento in " + documentos;
                    checkTipoDocumentos = true;
                    entraResta = false;
                }
                sql += ")  ";
            }

            if(checkTipoDocumentos)
            {
                if(checkabonoValor)
                {
                    entraResta = true;
                }
            }
            // Restar los documentos B o D
            if(entraResta) {

                sql += " - (SELECT IFNULL (Sum(Saldo),0) as Deuda FROM " + NOMBRE_TABLE_MOVIMIENTO + " WHERE ClienteID=C.ClienteID AND Saldo!=0 AND TipoDocumento in('B','D')) ";
            }

            sql += ") As Deuda";
            sql += " FROM ";
            StringBuilder sb = new StringBuilder();
            sb.append(sql);
            sb.append(NOMBRE_TABLE);
            sb.append(" AS C WHERE Deuda>0 AND ");
            sb.append(campo);
            sb.append(" like '%");
            sb.append(valor);
            sb.append("%' ");
            if (dia > 0) {
                if (par) {
                    sb.append(" AND");
                    sb.append(" ( substr(C.Ruta,1,1) = '0' or substr(C.Ruta,1,1) = '2' ) ");
                } else {
                    sb.append(" AND");
                    sb.append(" ( substr(C.Ruta,1,1) = '0' or substr(C.Ruta,1,1) = '1' ) ");
                }
                sb.append(" AND");
                sb.append(" substr(C.Ruta,");
                sb.append(dia + Constantes.INDICE_RUTA);
                sb.append(",1) = '1'");
            }

            sb.append(" ORDER by C.");
            sb.append(campoOrden);
            sb.append(" ASC");

            String SQL = sb.toString();
            Log.d("ClienteDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);
            Cliente cliente;
            double DeudaTotal = 0;
            while (cursor.moveToNext()) {
                cliente = new Cliente();
                cliente.iCliente = cursor.getInt(cursor.getColumnIndex(I_CLIENTE));
                cliente.ClienteID = cursor.getString(cursor.getColumnIndex(CLIENTE_ID));
                cliente.Nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
                cliente.Deuda = cursor.getDouble(cursor.getColumnIndex("Deuda"));
                cliente.Telefono = cursor.getString(cursor.getColumnIndex(TELEFONO));
                cliente.Email = cursor.getString(cursor.getColumnIndex(EMAIL));
                cliente.MontoLineaCredito = cursor.getDouble(cursor.getColumnIndex(MONTO_LINEA_CREDITO));
                DeudaTotal += cliente.Deuda;
                cliente.DeudaTotal = DeudaTotal;
                clientes.add(cliente);
            }

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            clientes = null;
        } finally {
            db.close();
        }
        return clientes;
    }

    public ArrayList<Cliente> buscarClientesNoSincronizados() {
        ArrayList<Cliente> clientes = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            clientes = new ArrayList<Cliente>();
            String SQL = "Select * from Cliente where Nuevo='1'";
            Log.d("ClienteDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                clientes.add(getCliente(cursor));
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            clientes = null;
        } finally {
            db.close();
        }
        return clientes;
    }

    public Cliente buscarCliente(String clienteID) {
        Cliente cliente = null;
        Cursor cursor = null;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            String SQL = "Select * from Cliente where ClienteID=?";
            Log.d("ClienteDAO", SQL);
            cursor = db.getDataBase().rawQuery(SQL, new String[]{clienteID});
            while (cursor.moveToNext()) {
                cliente = getCliente(cursor);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            cliente = null;
        } finally {
            db.close();
        }
        return cliente;
    }


    public boolean actualizarCliente(Cliente cliente) {
        boolean estado = false;
        ManagerDB db = ManagerDB.getInstance(context);
        try {
            db.openDataBase();
            db.getDataBase().update(NOMBRE_TABLE, getContentValues(cliente), "iCliente=? ", new String[]{String.valueOf(cliente.iCliente)});
            estado = true;
        } catch (Exception ex) {
            Log.e("ActualizarCliente DAO", ex.getMessage(), ex);
            estado = false;

        } finally {
            db.close();
        }
        return estado;
    }


}
