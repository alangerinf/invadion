package com.bitlicon.purolator.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Cliente implements Serializable {

    public long iCliente;
    public String ClienteID;
    public String Nombre;
    public String Direccion;
    public String Despacho;
    public String RUC;
    public String Telefono;
    public String Email;
    public String DNI;
    public String Giro;
    public String RepresentanteLegal;
    public String Encargado;
    public double MontoLineaCredito;
    public String FechaCreacion;
    public String FiltechID;
    public String PurolatorID;
    public String Ruta;
    public boolean Nuevo;
    public boolean Eliminar;
    public double TotalFiltech;
    public double TotalPurolator;
    public double DeudaTotal;
    public double DeudaFiltech;
    public double DeudaPurolator;
    public double Total;
    public double Recordar;
    public double Deuda;
    /*   public String Distrito;
    public String Departamento;
    public String Provincia;

    public String Postal;
    public String Fax;


    public double SaldoLineaCreditoSoles;
    public double SaldoLineaCreditoDolares;
    public String CodigoTransportista;

    */


    //Calculados
    //public boolean Sincronizado;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cliente{");
        sb.append("iCliente=").append(iCliente);
        sb.append(", ClienteID='").append(ClienteID).append('\'');
        sb.append(", Nombre='").append(Nombre).append('\'');
        sb.append(", Direccion='").append(Direccion).append('\'');
        sb.append(", Despacho='").append(Despacho).append('\'');
        sb.append(", RUC='").append(RUC).append('\'');
        sb.append(", Telefono='").append(Telefono).append('\'');
        sb.append(", Email='").append(Email).append('\'');
        sb.append(", DNI='").append(DNI).append('\'');
        sb.append(", Giro='").append(Giro).append('\'');
        sb.append(", RepresentanteLegal='").append(RepresentanteLegal).append('\'');
        sb.append(", Encargado='").append(Encargado).append('\'');
        sb.append(", MontoLineaCredito=").append(MontoLineaCredito);
        sb.append(", FechaCreacion='").append(FechaCreacion).append('\'');
        sb.append(", PurolatorID='").append(PurolatorID).append('\'');
        sb.append(", FiltechID='").append(FiltechID).append('\'');
        sb.append(", Ruta='").append(Ruta).append('\'');
        sb.append(", Nuevo=").append(Nuevo);
        sb.append('}');
        return sb.toString();
    }
}
