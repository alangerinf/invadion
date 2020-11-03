package com.bitlicon.purolator.entities;

import java.io.Serializable;

/**
 * Created by Edinson on 21/05/2015.
 */
public class Factura implements Serializable {

    public int IFactura;
    public String NumeroFactura;
    public String VendedorID;
    public String ClienteID;
    public double TotalImporte;
    public double TotalImpuestos;
    public String FechaFactura;

    public double TotalDescuento;

    public double TotalOrden;

    public double Descuento3;

    public double Descuento2;

    public double Descuento1;

    public String TerminoVenta;

    public double DescuentoItem;
    public int Linea;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Factura{");
        sb.append("IFactura=").append(IFactura);
        sb.append(", NumeroFactura='").append(NumeroFactura).append('\'');
        sb.append(", VendedorID='").append(VendedorID).append('\'');
        sb.append(", ClienteID='").append(ClienteID).append('\'');
        sb.append(", TotalImporte=").append(TotalImporte);
        sb.append(", TotalImpuestos=").append(TotalImpuestos);
        sb.append(", FechaFactura='").append(FechaFactura).append('\'');
        sb.append(", TotalDescuento=").append(TotalDescuento);
        sb.append(", TotalOrden=").append(TotalOrden);
        sb.append(", Descuento3=").append(Descuento3);
        sb.append(", Descuento2=").append(Descuento2);
        sb.append(", Descuento1=").append(Descuento1);
        sb.append(", TerminoVenta='").append(TerminoVenta).append('\'');
        sb.append(", DescuentoItem=").append(DescuentoItem);
        sb.append('}');
        return sb.toString();
    }
}
