package com.bitlicon.purolator.entities;

import java.io.Serializable;

/**
 * Created by dianewalls on 22/05/2015.
 */
public class Movimiento implements Serializable {

    public int iMovimiento;
    public double Saldo;
    public String FechaVencimiento;
    public String TipoDocumento;
    public String NumDocumento;
    public String ClienteID;

    public String VendedorID;
    public String MovimientoID;
    public double Monto;
    public String Pagada;
    public String Letban;
    public String Agencia;
    public String FechaDocumento;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Movimiento{");
        sb.append("iMovimiento=").append(iMovimiento);
        sb.append(", Saldo=").append(Saldo);
        sb.append(", FechaVencimiento='").append(FechaVencimiento).append('\'');
        sb.append(", TipoDocumento='").append(TipoDocumento).append('\'');
        sb.append(", NumDocumento='").append(NumDocumento).append('\'');
        sb.append(", ClienteID='").append(ClienteID).append('\'');
        sb.append(", VendedorID='").append(VendedorID).append('\'');
        sb.append(", MovimientoID='").append(MovimientoID).append('\'');
        sb.append(", Monto=").append(Monto);
        sb.append(", Pagada='").append(Pagada).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
